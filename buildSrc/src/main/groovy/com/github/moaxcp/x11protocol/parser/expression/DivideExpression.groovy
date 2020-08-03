package com.github.moaxcp.x11protocol.parser.expression

class DivideExpression extends OpExpression {
    DivideExpression() {
        op = '/'
    }
    String getExpression() {
        expressions.collect{
            if(it instanceof OpExpression && (it.op == '+' || it.op == '-' || it.op == '*' || it.op == '/')) {
                return "(${it.expression})"
            }
            it.expression
        }.join(" $op ")
    }
}
