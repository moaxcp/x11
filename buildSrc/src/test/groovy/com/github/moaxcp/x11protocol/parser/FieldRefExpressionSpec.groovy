package com.github.moaxcp.x11protocol.parser

import spock.lang.Specification

class FieldRefExpressionSpec extends Specification {
    def 'name'() {
        given:
        FieldRefExpression expression = new FieldRefExpression(name:'name')

        expect:
        expression.getExpression() == 'name'
    }
}
