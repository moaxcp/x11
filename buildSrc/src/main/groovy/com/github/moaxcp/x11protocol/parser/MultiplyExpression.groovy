package com.github.moaxcp.x11protocol.parser

class MultiplyExpression extends OpExpression {
    MultiplyExpression() {
        op = '*'
    }
    String getExpression() {
        expressions.join(" $op ")
    }
}
