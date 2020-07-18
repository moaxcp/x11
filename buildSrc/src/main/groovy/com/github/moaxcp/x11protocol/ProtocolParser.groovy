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
    private final Conventions conventions = new Conventions()

    ParseResult parse() {
        ParseResult result = new ParseResult()
        GPathResult xml = new XmlSlurper().parse(file)
        result.packageName = basePackage + '.' + (String) xml.@header
        packageName = result.packageName
        Map<String, String> typeDefs = parseTypeDefs(xml)
        conventions.putAllTypes(typeDefs)
        List<TypeSpec> types = xml.struct
            .findAll { !conventions.filterName((String) it.@name) }
            .collect { parseStruct(it) }
        result.javaTypes.put('struct', types)
        return result
    }

    Map<String, String> parseTypeDefs(GPathResult xml) {
        Map<String, String> types = [:]
        types.putAll(xml.xidtype.collectEntries {
            [((String) it.@name):'int']
        })
        types.putAll(xml.typedef.collectEntries {
            [((String) it.@newname):conventions.getX11ToJavaType((String) it.@oldname)]
        })
        types.putAll(xml.xidunion.collectEntries {
            [((String) it.@name):'int']
        })

        return types
    }

    TypeSpec parseStruct(GPathResult struct) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(conventions.getX11ToJavaType((String) struct.@name))
        List<FieldSpec> fields = parseFields(struct)
        builder.addFields(fields)
        return builder.build();
    }

    List<FieldSpec> parseFields(GPathResult node) {
        return node.field.collect {
            TypeName typeName = ClassName.get(packageName, conventions.getX11ToJavaType((String) it.@type))
            FieldSpec.builder(typeName, conventions.convertX11VariableNameToJava((String) it.@name)).build()
        }
    }
}
