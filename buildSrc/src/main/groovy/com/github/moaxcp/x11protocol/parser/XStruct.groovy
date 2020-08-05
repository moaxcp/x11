package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.generator.Conventions
import com.squareup.javapoet.*
import groovy.transform.ToString
import groovy.util.slurpersupport.Node
import javax.lang.model.element.Modifier

@ToString(includeSuperProperties = true, includePackage = false, includes = ['name', 'type', 'protocol'])
class XStruct extends XType {
    List<XUnit> protocol = []

    String getJavaClassName() {
        Conventions."get${type.capitalize()}JavaName"(name)
    }

    @Override
    ClassName getJavaType() {
        (ClassName) super.javaType
    }

    static XStruct getXStruct(XResult result, Node node) {
        XStruct struct = new XStruct()
        struct.result = result
        struct.name = node.attributes().get('name')
        struct.type = node.name()
        node.childNodes().each { Node it ->
            switch(it.name()) {
                case 'field':
                    String fieldName = it.attributes().get('name')
                    String fieldType = it.attributes().get('type')
                    String fieldEnum = it.attributes().get('enum')
                    String fieldMask = it.attributes().get('mask')
                    XField field = new XField(result:result, type:fieldType, enumType:fieldEnum, maskType: fieldMask, name:fieldName)
                    struct.protocol.add(field)
                    break
                case 'pad':
                    int padBytes = Integer.valueOf((String) it.attributes().get('bytes'))
                    XPad pad = new XPad(bytes:padBytes)
                    struct.protocol.add(pad)
                    break
                default:
                    throw new IllegalArgumentException("cannot parse $it")
            }
        }

        return struct
    }

    TypeSpec getTypeSpec() {
        List<FieldSpec> fields = protocol.findAll {
            it instanceof PropertyXUnit && !it.localOnly
        }.collect { PropertyXUnit it ->
            it.member
        }
        return TypeSpec.classBuilder(javaType)
            .addFields(fields)
            .addMethod(readMethod)
            .addMethod(writeMethod)
            .addMethods(getMaskMethods())
            .build()
    }

    MethodSpec getReadMethod() {
        CodeBlock.Builder readProtocol = CodeBlock.builder()
        protocol.each {
            readProtocol.addStatement(it.readCode)
        }

        CodeBlock.Builder setters = CodeBlock.builder()
        protocol.findAll {
            it instanceof PropertyXUnit
        }.each { PropertyXUnit it ->
            setters.addStatement('$L.$L($L)', type, it.setterName, it.javaName)
        }

        return MethodSpec.methodBuilder("read${javaClassName}")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(javaType)
            .addCode(readProtocol.build())
            .addStatement('$1T $2L = new $1T()', javaType, type)
            .addCode(setters.build())
            .addStatement('return $L', type)
            .build()
    }

    MethodSpec getWriteMethod() {
        CodeBlock.Builder writeProtocol = CodeBlock.builder()
        protocol.each {
            writeProtocol.addStatement(it.writeCode)
        }
        return MethodSpec.methodBuilder("write${javaClassName}")
            .addModifiers(Modifier.PUBLIC)
            .addCode(writeProtocol.build())
            .build()
    }

    List<MethodSpec> getMaskMethods() {
        List<MethodSpec> methods = protocol.collect {
            if(it instanceof XField && it.maskType) {
                [
                    MethodSpec.methodBuilder("${it.javaName}Enable")
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(it.javaMaskTypeName, 'mask')
                        .addStatement('$1L = mask.enableFor($1L)', it.javaName)
                        .build(),
                    MethodSpec.methodBuilder("${it.javaName}Disable")
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(it.javaMaskTypeName, 'mask')
                        .addStatement('$1L = mask.disableFor($1L)', it.javaName)
                        .build()
                ]
            }
        }.flatten().findAll { it }

        return methods
    }
}
