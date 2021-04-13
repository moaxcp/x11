package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeSpec 

interface JavaType {
    String getBasePackage()
    String getSimpleName()
    ClassName getClassName()
    ClassName getBuilderClassName()
    List<TypeSpec> getTypeSpecs()
    JavaProperty getJavaProperty(String name)
}
