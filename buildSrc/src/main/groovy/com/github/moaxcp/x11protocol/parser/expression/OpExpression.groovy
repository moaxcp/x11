package com.github.moaxcp.x11protocol.parser.expression

import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.parser.expression.Expressions.selectCastUp

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

    TypeName getTypeName() {
        List<TypeName> types = expressions.collect { it.typeName }
        types.inject { result, i ->
            return selectCastUp(result, i)
        }
    }
}
