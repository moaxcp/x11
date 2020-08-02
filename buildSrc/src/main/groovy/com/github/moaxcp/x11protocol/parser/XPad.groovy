package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class XPad implements XUnit {
    int bytes

    @Override
    CodeBlock getReadCode() {
        return CodeBlock.of("in.readPad($bytes)")
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.of("out.writePad($bytes)")
    }
}
