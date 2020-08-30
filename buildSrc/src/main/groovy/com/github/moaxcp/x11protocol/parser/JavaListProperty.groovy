package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.Expression
import com.squareup.javapoet.TypeName

abstract class JavaListProperty extends JavaProperty {
    String name
    TypeName baseTypeName
    TypeName typeName
    Expression lengthExpression
    String lengthField
    boolean readOnly
    boolean localOnly
}
