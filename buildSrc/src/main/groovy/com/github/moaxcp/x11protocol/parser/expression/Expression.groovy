package com.github.moaxcp.x11protocol.parser.expression

interface Expression {
    List<String> getFieldRefs()
    String getExpression()
}