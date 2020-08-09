package com.github.moaxcp.x11protocol.parser.expression

interface Expression {
    List<FieldRefExpression> getFieldRefs()
    List<ParamRefExpression> getParamRefs()
    String getExpression()
}