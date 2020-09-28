package com.github.moaxcp.x11protocol.parser.expression

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName
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

    @Override
    TypeName getTypeName() {
        return TypeName.INT
    }

    CodeBlock getExpression() {
        return CodeBlock.of(value)
    }
}
