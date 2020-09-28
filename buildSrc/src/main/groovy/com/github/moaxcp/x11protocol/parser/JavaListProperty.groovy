package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.Expression
import com.github.moaxcp.x11protocol.parser.expression.Expressions
import com.squareup.javapoet.TypeName

abstract class JavaListProperty extends JavaProperty {
    final Expression lengthExpression
    String lengthField
    
    JavaListProperty(JavaType javaType, XUnitListField field) {
        super(javaType, field)
        lengthExpression = Expressions.getExpression(javaType, field.lengthExpression)
    }
    
    abstract TypeName getBaseTypeName()
}
