package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.TypeName

/**
 * Defines a {@link JavaUnit} which can be a read parameter in the read method.
 */
interface JavaReadParameter extends JavaUnit {
    String getName()

    boolean isReadParam()
    void setReadParam(boolean readParam)

    TypeName getReadTypeName()
    void setReadTypeName(TypeName readTypeName)
}