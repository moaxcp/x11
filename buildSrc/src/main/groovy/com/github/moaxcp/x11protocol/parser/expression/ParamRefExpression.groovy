package com.github.moaxcp.x11protocol.parser.expression

import com.github.moaxcp.x11protocol.generator.Conventions
import com.squareup.javapoet.CodeBlock
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includePackage = false)
@EqualsAndHashCode
class ParamRefExpression implements Expression {
    String paramName
    String x11Type

    @Override
    List<String> getFieldRefs() {
        return []
    }

    @Override
    List<ParamRefExpression> getParamRefs() {
        return [this]
    }

    CodeBlock getExpression() {
        return CodeBlock.of(Conventions.convertX11VariableNameToJava(paramName))
    }
}
