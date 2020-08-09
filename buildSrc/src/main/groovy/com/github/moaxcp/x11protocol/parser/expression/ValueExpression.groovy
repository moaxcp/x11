package com.github.moaxcp.x11protocol.parser.expression

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includePackage = false)
@EqualsAndHashCode
class ValueExpression implements Expression {
    String value

    @Override
    List<FieldRefExpression> getFieldRefs() {
        return []
    }

    @Override
    List<ParamRefExpression> getParamRefs() {
        return []
    }

    String getExpression() {
        return value
    }
}
