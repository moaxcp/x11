package com.github.moaxcp.x11protocol.xcbparser.expression

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName
import groovy.transform.EqualsAndHashCode

import static com.github.moaxcp.x11protocol.xcbparser.expression.Expressions.castOrder

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

    @Override
    CodeBlock getExpression(TypeName primative) {
        if(castOrder(getTypeName()) > castOrder(primative)) {
            return CodeBlock.of('($T) ($L)', primative, expression)
        }
        return expression
    }
}
