package com.github.moaxcp.x11protocol.parser.expression

import com.squareup.javapoet.CodeBlock

class AndExpression extends OpExpression {
    AndExpression() {
        op = '&'
    }

    CodeBlock getExpression() {
        return CodeBlock.join(expressions.collect{
            CodeBlock.of('($L)', it.expression)
        }, " $op ")
    }
}
