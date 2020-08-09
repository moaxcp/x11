package com.github.moaxcp.x11protocol.parser.expression

class UnopExpression implements Expression {
    String op
    Expression unExpression

    @Override
    List<FieldRefExpression> getFieldRefs() {
        return unExpression.fieldRefs
    }

    @Override
    List<ParamRefExpression> getParamRefs() {
        return unExpression.paramRefs
    }

    @Override
    String getExpression() {
        return "op (${unExpression.expression})"
    }
}
