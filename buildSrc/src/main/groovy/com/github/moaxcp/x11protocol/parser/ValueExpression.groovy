package com.github.moaxcp.x11protocol.parser

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class ValueExpression implements Expression {
    String value

    String getExpression() {
        return value
    }
}
