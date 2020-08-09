package com.github.moaxcp.x11protocol.parser.expression

import com.github.moaxcp.x11protocol.generator.Conventions
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includePackage = false)
@EqualsAndHashCode
class FieldRefExpression implements Expression {
    String fieldName

    @Override
    List<FieldRefExpression> getFieldRefs() {
        return [this]
    }

    @Override
    List<ParamRefExpression> getParamRefs() {
        return []
    }

    String getExpression() {
        return Conventions.convertX11VariableNameToJava(fieldName)
    }
}
