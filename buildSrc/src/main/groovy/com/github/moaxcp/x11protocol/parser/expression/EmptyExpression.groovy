package com.github.moaxcp.x11protocol.parser.expression

import com.squareup.javapoet.CodeBlock

class EmptyExpression implements Expression {

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
        throw new UnsupportedOperationException('cannot generate code for EmptyExpression')
    }
}
