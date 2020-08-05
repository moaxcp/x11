package com.github.moaxcp.x11protocol.parser.expression


import spock.lang.Specification

class FieldRefExpressionSpec extends Specification {
    def 'name'() {
        given:
        FieldRefExpression expression = new FieldRefExpression(fieldName:'name')

        expect:
        expression.getExpression() == 'name'
    }
}
