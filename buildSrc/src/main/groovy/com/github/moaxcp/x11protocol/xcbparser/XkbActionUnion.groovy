package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec

import javax.lang.model.element.Modifier

class XkbActionUnion extends JavaUnion {
  XkbActionUnion(Map map) {
    super(map)
  }

  @Override
  TypeSpec getTypeSpec() {
    return TypeSpec.interfaceBuilder(className)
        .addSuperinterface(ClassName.get(basePackage, 'XObject'))
        .addModifiers(Modifier.PUBLIC)
        .addMethod(readMethod)
        .addMethod(writeMethod)
        .build()
  }

  @Override
  MethodSpec getReadMethod() {
    return MethodSpec.methodBuilder("read${simpleName}")
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
        .returns(className)
        .addParameter(ClassName.get(basePackage, 'X11Input'), 'in')
        .addException(IOException)
        .addStatement("return null")
        .build()
  }

  @Override
  MethodSpec getWriteMethod() {
    return MethodSpec.methodBuilder("write")
        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
        .addParameter(ClassName.get(basePackage, 'X11Output'), 'out')
        .addException(IOException)
        .build()
  }
}
