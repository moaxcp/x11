package com.github.moaxcp.x11protocol.parser

abstract class OpExpression implements Expression {
    private String op
    private List<Expression> expressions
}
