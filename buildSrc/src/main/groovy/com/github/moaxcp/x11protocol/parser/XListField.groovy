package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.Expression
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName

class XListField extends ResolvableProperty {
    Expression lengthExpression

    String getLengthField() {
        return null
    }

    @Override
    CodeBlock getReadCode() {
        return null
    }

    @Override
    CodeBlock getWriteCode() {
        return null
    }

    @Override
    FieldSpec getMember() {
        return null
    }

    @Override
    String getSetterName() {
        return null
    }

    @Override
    String getGetterName() {
        return null
    }

    @Override
    boolean isReadOnly() {
        return false
    }

    @Override
    boolean isLocalOnly() {
        return false
    }

    @Override
    String getJavaName() {
        return null
    }

    @Override
    TypeName getJavaTypeName() {
        return null
    }
}
