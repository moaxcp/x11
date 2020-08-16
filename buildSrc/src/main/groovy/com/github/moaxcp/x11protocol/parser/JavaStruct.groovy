package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.ParamRefExpression
import com.squareup.javapoet.*
import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.*

class JavaStruct extends JavaBaseObject {

    static JavaStruct javaStruct(XTypeStruct struct) {
        List<JavaUnit> protocol = struct.toJavaProtocol()

        String simpleName = getStructJavaName(struct.name)
        return new JavaStruct(
            superType: struct.superType,
            basePackage: struct.basePackage,
            javaPackage: struct.javaPackage,
            simpleName:simpleName,
            className:getStructTypeName(struct.javaPackage, struct.name),
            protocol:protocol
        )
    }

    @Override
    MethodSpec getReadMethod() {
        List<ParameterSpec> params = protocol.findAll {
            it instanceof JavaListProperty
        }.collect { JavaListProperty it ->
            it.lengthExpression.paramRefs
        }.flatten().collect { ParamRefExpression it ->
            ParameterSpec.builder(x11PrimativeToJavaTypeName(it.x11Type), convertX11VariableNameToJava(it.paramName)).build()
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

    @Override
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
