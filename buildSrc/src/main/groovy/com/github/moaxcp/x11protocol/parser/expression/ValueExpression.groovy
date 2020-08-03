package com.github.moaxcp.x11protocol.parser.expression

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includePackage = false)
@EqualsAndHashCode
class ValueExpression implements Expression {
    String value

    String getExpression() {
        return value
    }
}
