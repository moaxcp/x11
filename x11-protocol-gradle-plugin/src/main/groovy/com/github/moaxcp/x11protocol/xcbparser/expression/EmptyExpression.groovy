package com.github.moaxcp.x11protocol.xcbparser.expression

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

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
    TypeName getTypeName() {
        throw new UnsupportedOperationException('cannot get type for empty expression')
    }

    @Override
    CodeBlock getExpression() {
        throw new UnsupportedOperationException('cannot generate code for EmptyExpression')
    }

    @Override
    CodeBlock getExpression(TypeName primative) {
        throw new UnsupportedOperationException('cannot generate code for EmptyExpression')
    }
}
