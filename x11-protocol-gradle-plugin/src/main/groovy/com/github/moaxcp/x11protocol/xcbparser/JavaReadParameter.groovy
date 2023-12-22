package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.TypeName

/**
 * Defines a {@link JavaUnit} which can be a read parameter in the read method.
 */
interface JavaReadParameter extends JavaUnit {
    boolean isReadParam()
    void setReadParam(boolean readParam)

    TypeName getReadTypeName()
    void setReadTypeName(TypeName readTypeName)
}