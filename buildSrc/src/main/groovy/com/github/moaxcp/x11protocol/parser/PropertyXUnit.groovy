package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.FieldSpec

interface PropertyXUnit extends LocalVariableXUnit {
    FieldSpec getMember()
    boolean isReadOnly()
}