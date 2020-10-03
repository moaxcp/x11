package com.github.moaxcp.x11protocol.parser.expression


import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

interface Expression {
    List<FieldRefExpression> getFieldRefs()
    List<ParamRefExpression> getParamRefs()
    TypeName getTypeName()
    CodeBlock getExpression()
    CodeBlock getExpression(TypeName primative)
}