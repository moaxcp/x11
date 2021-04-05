package com.github.moaxcp.x11protocol.xcbparser.expression

import com.squareup.javapoet.CodeBlock

class AddExpression extends OpExpression {
    AddExpression() {
        op = '+'
    }

    CodeBlock getExpression() {
        return CodeBlock.join(expressions*.expression, " $op ")
    }
}
