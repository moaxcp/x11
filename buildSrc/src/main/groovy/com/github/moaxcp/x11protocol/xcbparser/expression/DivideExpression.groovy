package com.github.moaxcp.x11protocol.xcbparser.expression

import com.squareup.javapoet.CodeBlock

class DivideExpression extends OpExpression {
    DivideExpression() {
        op = '/'
    }

    CodeBlock getExpression() {
        return CodeBlock.join(expressions.collect{
            if(it instanceof OpExpression && (it.op == '+' || it.op == '-' || it.op == '*' || it.op == '/')) {
                return CodeBlock.of('($L)', it.expression)
            }
            it.expression
        }," $op ")
    }
}
