package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock

interface JavaUnit {
    CodeBlock getReadCode()
    CodeBlock getWriteCode()
}