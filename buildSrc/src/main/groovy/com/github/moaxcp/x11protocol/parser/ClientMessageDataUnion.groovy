package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
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
            .build()
    }

    @Override
    MethodSpec getWriteMethod() {
        return null
    }
}
