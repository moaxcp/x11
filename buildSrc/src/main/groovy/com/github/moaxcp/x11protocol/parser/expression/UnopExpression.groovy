package com.github.moaxcp.x11protocol.parser.expression

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

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
    TypeName getTypeName() {
        return unExpression.typeName
    }

    @Override
    CodeBlock getExpression() {
        return CodeBlock.of("$op (\$L)", unExpression.expression)
    }
}
