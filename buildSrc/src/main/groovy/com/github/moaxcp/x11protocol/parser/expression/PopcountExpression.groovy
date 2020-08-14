package com.github.moaxcp.x11protocol.parser.expression

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock

class PopcountExpression implements Expression {
    String basePackage
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
    CodeBlock getExpression() {
        return CodeBlock.of('$T.popcount($L)', ClassName.get(basePackage, 'Popcount'), field.expression)
    }
}
