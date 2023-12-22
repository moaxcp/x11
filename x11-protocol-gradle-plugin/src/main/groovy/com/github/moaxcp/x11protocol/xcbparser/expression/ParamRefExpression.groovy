package com.github.moaxcp.x11protocol.xcbparser.expression

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName
import groovy.transform.EqualsAndHashCode

import static com.github.moaxcp.x11protocol.generator.Conventions.x11PrimativeToStorageTypeName
import static com.github.moaxcp.x11protocol.xcbparser.expression.Expressions.castOrder

@EqualsAndHashCode
class ParamRefExpression implements Expression {
    String paramName
    String x11Type

    @Override
    List<String> getFieldRefs() {
        return []
    }

    @Override
    List<ParamRefExpression> getParamRefs() {
        return [this]
    }

    @Override
    TypeName getTypeName() {
        return x11PrimativeToStorageTypeName(x11Type)
    }

    CodeBlock getExpression() {
        return CodeBlock.of(paramName)
    }

    @Override
    CodeBlock getExpression(TypeName primative) {
        if(castOrder(getTypeName()) > castOrder(primative)) {
            return CodeBlock.of('($T) ($L)', primative, expression)
        }
        return expression
    }
}
