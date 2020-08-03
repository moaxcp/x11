package com.github.moaxcp.x11protocol.parser.expression

abstract class OpExpression implements Expression {
    String op
    List<Expression> expressions = []
}
