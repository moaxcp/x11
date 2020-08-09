package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.ParamRefExpression
import com.squareup.javapoet.*
import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.*

class JavaStruct implements JavaType {
    String basePackage
    String simpleName
    ClassName className
    List<JavaUnit> protocol

    static JavaStruct javaStruct(XTypeStruct struct) {
        List<JavaUnit> protocol = struct.protocol.collect {
            it.getJavaUnit()
        }

        protocol.eachWithIndex { JavaUnit entry, int i ->
            if(entry instanceof JavaPadAlign) {
                entry.list = protocol[i - 1]
            }
        }

        String simpleName = getStructJavaName(struct.name)
        return new JavaStruct(
            basePackage: struct.basePackage,
            simpleName:simpleName,
            className:getStructTypeName(struct.javaPackage, struct.name),
            protocol:protocol
        )
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
        return TypeSpec.classBuilder(className)
            .addAnnotation(ClassName.get('lombok', 'Data'))
            .addFields(fields)
            .addMethod(readMethod)
            .addMethod(writeMethod)
            .addMethods(methods)
            .build()
    }

    MethodSpec getReadMethod() {
        List<ParameterSpec> params = protocol.findAll {
            it instanceof JavaListProperty
        }.collect { JavaListProperty it ->
            it.lengthExpression.paramRefs
        }.flatten().collect { ParamRefExpression it ->
            ParameterSpec.builder(x11PrimativeToJavaTypeName(it.x11Primative), convertX11VariableNameToJava(it.paramName)).build()
        }

        CodeBlock.Builder readProtocol = CodeBlock.builder()
        protocol.each {
            readProtocol.addStatement(it.readCode)
        }

        CodeBlock.Builder setters = CodeBlock.builder()
        protocol.findAll {
            it instanceof JavaProperty
        }.each { JavaProperty it ->
            setters.addStatement('$L.$L($L)', 'struct', it.setterName, it.name)
        }

        return MethodSpec.methodBuilder("read${simpleName}")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(className)
            .addParameter(ClassName.get(basePackage, 'X11Input'), 'in')
            .addParameters(params)
            .addException(IOException)
            .addCode(readProtocol.build())
            .addStatement('$1T $2L = new $1T()', className, 'struct')
            .addCode(setters.build())
            .addStatement('return $L', 'struct')
            .build()
    }

    MethodSpec getWriteMethod() {
        CodeBlock.Builder writeProtocol = CodeBlock.builder()
        protocol.each {
            writeProtocol.addStatement(it.writeCode)
        }
        return MethodSpec.methodBuilder("write${simpleName}")
            .addModifiers(Modifier.PUBLIC)
            .addParameter(ClassName.get(basePackage, 'X11Output'), 'out')
            .addException(IOException)
            .addCode(writeProtocol.build())
            .build()
    }
}
