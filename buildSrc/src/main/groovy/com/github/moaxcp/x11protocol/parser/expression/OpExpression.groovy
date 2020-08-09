package com.github.moaxcp.x11protocol.parser.expression

abstract class OpExpression implements Expression {
    String op
    List<Expression> expressions = []

    @Override
    List<ParamRefExpression> getParamRefs() {
        return expressions.collect {
            it.paramRefs
        }.flatten()
    }

    @Override
    List<FieldRefExpression> getFieldRefs() {
        return expressions.collect {
            it.fieldRefs
        }.flatten()
    }
}
