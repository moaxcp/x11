package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includePackage = false)
@EqualsAndHashCode
class JavaPad implements JavaUnit {
    int bytes

    @Override
    CodeBlock getReadCode() {
        return CodeBlock.of("in.readPad($bytes)")
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.of("out.writePad($bytes)")
    }

    @Override
    int getSize() {
        return bytes
    }
}
