package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock

interface XUnit {
    CodeBlock getReadCode()
    CodeBlock getWriteCode()
}