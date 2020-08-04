package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.FieldSpec

/**
 *
 */
interface PropertyXUnit extends JavaVariableXUnit {
    FieldSpec getMember()
    String getSetterName()
    String getGetterName()
}