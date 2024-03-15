package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeSpec 

interface JavaType {
    String getBasePackage()
    String getSimpleName()
    ClassName getClassName()
    Optional<String> getXUnitSubtype()
    ClassName getBuilderClassName()
    TypeSpec getTypeSpec()
    JavaProperty getJavaProperty(String name)
}
