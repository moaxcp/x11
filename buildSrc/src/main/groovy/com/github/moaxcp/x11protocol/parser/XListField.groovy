package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.Expression
import com.squareup.javapoet.CodeBlock

class XListField extends ResolvableVariable {
    Expression lengthExpression

    @Override
    CodeBlock getReadCode() {
        return null
    }

    @Override
    CodeBlock getWriteCode() {
        return null
    }
}
