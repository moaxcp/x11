package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

interface JavaUnit {
    JavaType getJavaType()
    TypeName getTypeName()
    XUnit getXUnit()
    CodeBlock getReadCode()
    CodeBlock getWriteCode()
    void setReadParam(boolean readParam)
    boolean isReadParam()
    /**
     * expression of size in bytes
     * @return
     */
    CodeBlock getSizeExpression()
    /**
     * returns the known fixed size or empty if not known
     * @return
     */
    Optional<Integer> getFixedSize()
}