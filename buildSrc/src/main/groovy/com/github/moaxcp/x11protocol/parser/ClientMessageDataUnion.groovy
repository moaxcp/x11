package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.*
import javax.lang.model.element.Modifier

class ClientMessageDataUnion extends JavaUnion {
    @Override
    TypeSpec getTypeSpec() {
        return TypeSpec.interfaceBuilder(className)
            .addModifiers(Modifier.PUBLIC)
            .addSuperinterface(ClassName.get(basePackage, 'XObject'))
            .addMethod(readMethod)
            .build()
    }

    @Override
    MethodSpec getReadMethod() {
        return MethodSpec.methodBuilder("read${simpleName}")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(className)
            .addParameter(ClassName.get(basePackage, 'X11Input'), 'in')
            .addParameter(TypeName.BYTE, 'format')
            .addException(IOException)
            .addCode(CodeBlock.builder()
                .beginControlFlow('switch(format)')
                .add('case 8:\n')
                .indent()
                .addStatement('return new $T(in.readCard8(20))', ClassName.get(javaPackage, 'ClientMessageData8'))
                .unindent()
                .add('case 16:\n')
                .indent()
                .addStatement('return new $T(in.readCard16(10))', ClassName.get(javaPackage, 'ClientMessageData16'))
                .unindent()
                .add('case 32:\n')
                .indent()
                .addStatement('return new $T(in.readCard32(5))', ClassName.get(javaPackage, 'ClientMessageData32'))
                .unindent()
                .add('default:\n')
                .indent()
                .addStatement('throw new IllegalArgumentException("format must be 8, 16, or 32. Got: " + format + ".")')
                .unindent()
                .endControlFlow()
                .build()
            )
            .build()
    }

    @Override
    MethodSpec getWriteMethod() {
        return null
    }
}
