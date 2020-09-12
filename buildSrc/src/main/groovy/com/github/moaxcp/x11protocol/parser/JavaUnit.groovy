package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock

interface JavaUnit {
    JavaType getJavaType()
    XUnit getXUnit()
    CodeBlock getReadCode()
    CodeBlock getWriteCode()
    /**
     * expression of size in bytes
     * @return
     */
    CodeBlock getSize()
}