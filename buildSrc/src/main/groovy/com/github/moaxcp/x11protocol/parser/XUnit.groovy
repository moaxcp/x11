package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock

/**
 * A single unit within the x protocol. XUnit can be read from an input stream and written to an output stream.
 */
interface XUnit {
    CodeBlock getReadCode()
    CodeBlock getWriteCode()
}