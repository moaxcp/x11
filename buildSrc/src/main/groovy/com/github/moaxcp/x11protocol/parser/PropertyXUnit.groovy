package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName

/**
 *
 */
interface PropertyXUnit extends XUnit {
    String getJavaName()
    TypeName getJavaTypeName()
    FieldSpec getMember()
    String getSetterName()
    String getGetterName()
    boolean isReadOnly()
    void setReadOnly(boolean readOnly)
    boolean isLocalOnly()
    void setLocalOnly(boolean localOnly)
}