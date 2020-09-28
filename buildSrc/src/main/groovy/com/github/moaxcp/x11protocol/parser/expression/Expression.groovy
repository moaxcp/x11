package com.github.moaxcp.x11protocol.parser.expression


import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

interface Expression {
    abstract List<FieldRefExpression> getFieldRefs()
    abstract List<ParamRefExpression> getParamRefs()
    TypeName getTypeName()
    abstract CodeBlock getExpression()
}