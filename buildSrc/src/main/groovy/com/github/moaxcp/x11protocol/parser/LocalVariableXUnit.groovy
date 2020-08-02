package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.TypeName

interface LocalVariableXUnit extends XUnit {
    TypeName getJavaType()
    String getJavaName()
}
