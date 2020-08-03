package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.TypeName

interface JavaVariableXUnit extends XUnit {
    String getJavaName()
    TypeName getJavaTypeName()
}