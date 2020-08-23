package com.github.moaxcp.x11protocol.parser.expression

import com.squareup.javapoet.CodeBlock

class SumOfExpression implements Expression {
    String ref
    Expression expression

    @Override
    List<FieldRefExpression> getFieldRefs() {
        return []
    }

    @Override
    List<ParamRefExpression> getParamRefs() {
        return []
    }

    @Override
    CodeBlock getExpression() {
        return null
    }
}
