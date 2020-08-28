package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock

class JavaPadAlign implements JavaUnit {
    int align
    JavaListProperty list

    @Override
    CodeBlock getReadCode() {
        if(align == 4) {
            return CodeBlock.of("in.readPadAlign(${list.lengthExpression.expression})")
        }
        return CodeBlock.of("in.readPadAlign($align, ${list.lengthExpression.expression})")
    }

    @Override
    CodeBlock getWriteCode() {
        if(align == 4) {
            return CodeBlock.of("out.writePadAlign(${list.lengthExpression.expression})")
        }
        return CodeBlock.of("out.writePadAlign($align, ${list.lengthExpression.expression})")
    }

    @Override
    int getSize() {
        return 0
    }
}
