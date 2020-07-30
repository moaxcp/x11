package com.github.moaxcp.x11protocol.parser

class FieldRefExpression implements Expression {
    private String name

    FieldRefExpression(String name) {
        this.name = name
    }

    String getExpression() {
        name
    }
}
