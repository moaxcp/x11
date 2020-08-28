package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock

class JavaRequiredStartAlign implements JavaUnit {
    int align

    @Override
    CodeBlock getReadCode() {
        return CodeBlock.of('//todo align')
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.of('//todo align')
    }

    @Override
    int getSize() {
        return 0
    }
}
