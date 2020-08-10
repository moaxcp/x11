package com.github.moaxcp.x11protocol.parser.expression

import com.squareup.javapoet.CodeBlock

interface Expression {
    List<FieldRefExpression> getFieldRefs()
    List<ParamRefExpression> getParamRefs()
    CodeBlock getExpression()
}