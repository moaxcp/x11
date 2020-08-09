package com.github.moaxcp.x11protocol.parser.expression

import com.github.moaxcp.x11protocol.generator.Conventions
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includePackage = false)
@EqualsAndHashCode
class ParamRefExpression implements Expression {
    String paramName
    String x11Primative

    @Override
    List<String> getFieldRefs() {
        return []
    }

    @Override
    List<ParamRefExpression> getParamRefs() {
        return [this]
    }

    String getExpression() {
        return Conventions.convertX11VariableNameToJava(paramName)
    }
}
