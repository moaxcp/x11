package com.github.moaxcp.x11protocol.parser.expression

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.parser.expression.Expressions.castOrder

class PopcountExpression implements Expression {
    FieldRefExpression field
    @Override
    List<FieldRefExpression> getFieldRefs() {
        return [field]
    }

    @Override
    List<ParamRefExpression> getParamRefs() {
        return []
    }

    @Override
    TypeName getTypeName() {
        return TypeName.BYTE
    }

    @Override
    CodeBlock getExpression() {
        return CodeBlock.of('$T.popcount($L)', ClassName.get(field.javaType.basePackage, 'Popcount'), field.expression)
    }

    @Override
    CodeBlock getExpression(TypeName primative) {
        if(castOrder(getTypeName()) > castOrder(primative)) {
            return CodeBlock.of('($T) ($L)', primative, expression)
        }
        return expression
    }
}
