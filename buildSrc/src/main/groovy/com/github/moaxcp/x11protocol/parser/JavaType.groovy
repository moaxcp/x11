package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeSpec

interface JavaType {
    String getSimpleName()
    ClassName getClassName()
    TypeSpec getTypeSpec()
    JavaProperty getField(String name)
}
