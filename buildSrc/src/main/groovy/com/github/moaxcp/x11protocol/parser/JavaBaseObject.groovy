package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.ParamRefExpression
import com.squareup.javapoet.*
import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.x11PrimativeToJavaTypeName

abstract class JavaBaseObject implements JavaType {
    String basePackage
    String javaPackage
    String simpleName
    Set<ClassName> superTypes = []
    ClassName className
    List<JavaUnit> protocol

    JavaProperty getField(String name) {
        (JavaProperty) protocol.find {
            it instanceof JavaProperty &&
            it.name == name
        }
    }

    boolean hasFields() {
        protocol.find {
            it instanceof JavaProperty
        }
    }

    @Override
    TypeSpec getTypeSpec() {
        List<FieldSpec> fields = protocol.findAll {
            it instanceof JavaProperty
        }.collect { JavaProperty it ->
            it.member
        }
        List<MethodSpec> methods = protocol.findAll {
            it instanceof JavaProperty
        }.collect { JavaProperty it ->
            it.methods
        }.flatten()
        TypeSpec.Builder typeSpec = TypeSpec.classBuilder(className)
            .addModifiers(Modifier.PUBLIC)
            .addFields(fields)
            .addMethod(readMethod)
            .addMethod(writeMethod)
            .addMethods(methods)
        if(superTypes) {
            typeSpec.addSuperinterfaces(superTypes)
        }
        if(hasFields()) {
            typeSpec.addAnnotation(ClassName.get('lombok', 'Data'))
                .addAnnotation(ClassName.get('lombok', 'AllArgsConstructor'))
                .addAnnotation(ClassName.get('lombok', 'NoArgsConstructor'))
        }

        typeSpec.build()
    }

    MethodSpec getReadMethod() {
        List<ParameterSpec> params = protocol.findAll {
            it instanceof JavaListProperty
        }.collect { JavaListProperty it ->
            it.lengthExpression.paramRefs
        }.flatten().collect { ParamRefExpression it ->
            ParameterSpec.builder(x11PrimativeToJavaTypeName(it.x11Type), it.paramName).build()
        }

        CodeBlock.Builder readProtocol = CodeBlock.builder()
        protocol.each {
            readProtocol.addStatement(it.readCode)
        }

        CodeBlock.Builder setters = CodeBlock.builder()
        protocol.findAll {
            it instanceof JavaProperty
        }.each { JavaProperty it ->
            setters.addStatement('$L.$L($L)', 'javaObject', it.setterName, it.name)
        }

        return MethodSpec.methodBuilder("read${simpleName}")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(className)
            .addParameter(ClassName.get(basePackage, 'X11Input'), 'in')
            .addParameters(params)
            .addException(IOException)
            .addCode(readProtocol.build())
            .addStatement('$1T $2L = new $1T()', className, 'javaObject')
            .addCode(setters.build())
            .addStatement('return $L', 'javaObject')
            .build()
    }

    MethodSpec getWriteMethod() {
        CodeBlock.Builder writeProtocol = CodeBlock.builder()
        protocol.each {
            writeProtocol.addStatement(it.writeCode)
        }
        return MethodSpec.methodBuilder("write")
            .addModifiers(Modifier.PUBLIC)
            .addParameter(ClassName.get(basePackage, 'X11Output'), 'out')
            .addException(IOException)
            .addCode(writeProtocol.build())
            .build()
    }
}
