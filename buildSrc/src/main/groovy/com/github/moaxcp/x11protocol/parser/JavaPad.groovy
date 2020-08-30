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
        return CodeBlock.builder().addStatement("in.readPad($bytes)").build()
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.builder().addStatement("out.writePad($bytes)").build()
    }

    @Override
    CodeBlock getSize() {
        return CodeBlock.of('$L', bytes)
    }
}
