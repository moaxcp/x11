package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

interface JavaUnit {
    String getName()
    JavaClass getJavaClass()
    TypeName getTypeName()
    XUnit getXUnit()
    CodeBlock getDeclareCode()
    CodeBlock getDefaultValue()
    CodeBlock getDeclareAndReadCode()
    CodeBlock getReadCode()
    void addBuilderCode(CodeBlock.Builder code)
    void addWriteCode(CodeBlock.Builder code)
    boolean isReadProtocol()
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