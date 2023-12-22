package com.github.moaxcp.x11protocol.xcbparser.expression

import com.squareup.javapoet.CodeBlock

class SubtractExpression extends OpExpression {
    SubtractExpression() {
        op = '-'
    }

    @Override
    CodeBlock getExpression() {
        CodeBlock.join(expressions.collect{it.expression}, " $op ")
    }
}
