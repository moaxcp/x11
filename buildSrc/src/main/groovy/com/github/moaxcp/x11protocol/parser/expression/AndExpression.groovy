package com.github.moaxcp.x11protocol.parser.expression

class AndExpression extends OpExpression {
    AndExpression() {
        op = '&'
    }

    String getExpression() {
        expressions.collect{
            "(${it.expression})"
        }.join(" $op ")
    }
}
