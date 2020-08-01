package com.github.moaxcp.x11protocol.parser

abstract class OpExpression implements Expression {
    String op
    List<Expression> expressions = []
}
