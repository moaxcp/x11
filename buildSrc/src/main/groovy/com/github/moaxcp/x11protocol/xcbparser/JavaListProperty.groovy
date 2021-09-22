package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.xcbparser.expression.Expression
import com.github.moaxcp.x11protocol.xcbparser.expression.Expressions
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

abstract class JavaListProperty extends JavaProperty {
    final Expression lengthExpression
    String lengthField
    
    JavaListProperty(JavaType javaType, XUnitListField field) {
        super(javaType, field)
        lengthExpression = Expressions.getExpression(javaType, field.lengthExpression)
    }
    
    @Override
    boolean isNonNull() {
        return true
    }
    
    abstract TypeName getBaseTypeName()

    @Override
    CodeBlock getDefaultValue() {
        return CodeBlock.of('null')
    }
}
