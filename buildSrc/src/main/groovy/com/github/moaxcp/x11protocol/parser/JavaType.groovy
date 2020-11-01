package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeSpec 

interface JavaType {
    String getBasePackage()
    String getSimpleName()
    ClassName getClassName()
    ClassName getBuilderClassName()
    TypeSpec getTypeSpec()
    JavaProperty getField(String name)
}
