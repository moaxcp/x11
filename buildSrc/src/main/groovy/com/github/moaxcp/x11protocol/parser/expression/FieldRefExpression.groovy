package com.github.moaxcp.x11protocol.parser.expression

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includePackage = false)
@EqualsAndHashCode
class FieldRefExpression implements Expression {
    String name

    String getExpression() {
        name
    }
}
