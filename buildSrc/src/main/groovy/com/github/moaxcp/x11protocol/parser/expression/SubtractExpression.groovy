package com.github.moaxcp.x11protocol.parser.expression

class SubtractExpression extends OpExpression {
    SubtractExpression() {
        op = '-'
    }

    @Override
    String getExpression() {
        expressions.collect{it.expression}.join(" $op ")
    }
}
