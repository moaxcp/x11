package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.Expression
import com.github.moaxcp.x11protocol.parser.expression.FieldRefExpression
import com.squareup.javapoet.TypeName

abstract class JavaListProperty extends JavaProperty {
    String name
    TypeName baseTypeName
    TypeName typeName
    Expression lengthExpression
    FieldRefExpression lengthField
    boolean readOnly
    boolean localOnly
}
