package com.github.moaxcp.x11protocol.parser.expression

import com.squareup.javapoet.CodeBlock
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class ValueExpression implements Expression {
    String value

    @Override
    List<FieldRefExpression> getFieldRefs() {
        return []
    }

    @Override
    List<ParamRefExpression> getParamRefs() {
        return []
    }

    CodeBlock getExpression() {
        return CodeBlock.of(value)
    }
}
