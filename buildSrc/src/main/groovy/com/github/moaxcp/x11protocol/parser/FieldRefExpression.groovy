package com.github.moaxcp.x11protocol.parser

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class FieldRefExpression implements Expression {
    String name

    String getExpression() {
        name
    }
}
