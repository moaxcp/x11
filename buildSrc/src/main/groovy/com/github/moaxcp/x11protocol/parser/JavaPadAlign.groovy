package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock

class JavaPadAlign implements JavaUnit {
    int align
    JavaListProperty list

    @Override
    CodeBlock getReadCode() {
        if(align == 4) {
            return CodeBlock.builder()
                .addStatement("in.readPadAlign(${list.lengthExpression.expression})")
                .build()
        }
        return CodeBlock.builder()
            .addStatement("in.readPadAlign($align, ${list.lengthExpression.expression})")
            .build()
    }

    @Override
    CodeBlock getWriteCode() {
        if(align == 4) {
            return CodeBlock.builder()
                .addStatement("out.writePadAlign(${list.lengthExpression.expression})")
                .build()
        }
        return CodeBlock.builder()
            .addStatement("out.writePadAlign($align, ${list.lengthExpression.expression})")
            .build()

    }

    @Override
    CodeBlock getSize() {
        return CodeBlock.of('$L', 0)
    }
}
