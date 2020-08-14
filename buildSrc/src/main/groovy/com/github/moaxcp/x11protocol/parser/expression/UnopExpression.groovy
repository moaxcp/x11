package com.github.moaxcp.x11protocol.parser.expression

import com.squareup.javapoet.CodeBlock

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
    CodeBlock getExpression() {
        return CodeBlock.of("$op (\$L)", unExpression.expression)
    }
}
