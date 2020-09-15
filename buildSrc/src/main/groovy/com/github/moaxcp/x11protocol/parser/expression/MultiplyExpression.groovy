package com.github.moaxcp.x11protocol.parser.expression

import com.squareup.javapoet.CodeBlock
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class MultiplyExpression extends OpExpression {
    MultiplyExpression() {
        op = '*'
    }

    CodeBlock getExpression() {
        return CodeBlock.join(expressions.collect{
            if(it instanceof OpExpression && (it.op == '+' || it.op == '-' || it.op == '/')) {
                return CodeBlock.of('($L)', it.expression)
            }
            it.expression
        }, " $op ")
    }
}
