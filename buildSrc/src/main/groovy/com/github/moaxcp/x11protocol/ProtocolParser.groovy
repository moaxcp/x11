package com.github.moaxcp.x11protocol

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import groovy.util.slurpersupport.GPathResult

class ProtocolParser {
    String packageName
    String basePackage
    File file
    private final ParseResult result = new ParseResult()
    private ImportResult importResult = new ImportResult()
    private boolean parsed

    ParseResult parse() {
        if(parsed) {
            throw new IllegalStateException("already parsed")
        }
        parsed = true
        GPathResult xml = new XmlSlurper().parse(file)
        result.xcbType = xml.@header
        result.packageName = basePackage + '.' + result.xcbType
        packageName = result.packageName
        setupImports()
        putAllTypeDefs(xml)
        ['struct', 'union', 'request', 'event', 'error'].each {
            addType(xml, it)
        }
        return result
    }

    private void addType(GPathResult xml, String type) {
        Map<String, TypeSpec> types = xml."$type"
            .findAll { !Conventions.filterType((String) it.@name) }
            .collectEntries { [((String) it.@name): parseType((GPathResult) it)] }
        result.javaTypes.putAll(types)
    }

    private void putAllTypeDefs(GPathResult xml) {
        Map<String, String> typeDefs = parseTypeDefs(xml)
        result.putAllDefinedTypes(typeDefs)
    }

    private void setupImports() {
        importResult = new ImportParser(basePackage: basePackage, file: file).parse()
    }

    static Map<String, String> parseTypeDefs(GPathResult xml) {
        Map<String, String> types = [:]
        types.putAll(xml.xidtype.collectEntries {
            [((String) it.@name):'int']
        })
        types.putAll(xml.typedef.collectEntries {
            [((String) it.@newname):Conventions.getX11ToJavaType((String) it.@oldname)]
        })
        types.putAll(xml.xidunion.collectEntries {
            [((String) it.@name):'int']
        })

        return types
    }

    private TypeSpec parseType(GPathResult type) {
        String typeName = Conventions.getX11ToJavaType((String) type.@name)
        TypeSpec.Builder builder = TypeSpec.classBuilder(typeName)
        List<FieldSpec> fields = parseFields(type)
        builder.addFields(fields)
        return builder.build();
    }

    private TypeName getTypeName(String typePackage, String type) {
        if(!type) {
            return null
        }
        ClassName.get(typePackage, type)
    }

    private List<FieldSpec> parseFields(GPathResult node) {
        return node.field.collect {
            String nodeType = it.@type
            TypeName typeName = getTypeName(packageName, result.getDefinedType(nodeType))
            if(!typeName) {
                typeName = getTypeName(packageName, importResult.definedTypes.get(nodeType))
            }
            if(!typeName) {
                importResult.x11ToJavaTypes.find { packageEntry ->
                    it.value.find { Map<String, String> packageConversion ->
                        if (packageConversion.key == nodeType) {
                            type = packageConversion.value
                            TypeName tyeName = ClassName.get(packageEntry.key, packageConversion.value)
                            return true
                        }
                        return false
                    }
                    return false
                }
            }
            if(!typeName) {
                String type = Conventions.getX11ToJavaType(nodeType)
                typeName = ClassName.get(packageName, type)
            }
            FieldSpec.builder(typeName, Conventions.convertX11VariableNameToJava((String) it.@name)).build()
        }
    }
}
