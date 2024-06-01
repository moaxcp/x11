package com.github.moaxcp.x11protocol.xcbparser.expression

import com.github.moaxcp.x11protocol.xcbparser.expression.ValueExpression
import spock.lang.Specification

class ValueExpressionSpec extends Specification {
    def 'constructor'() {
        given:
        ValueExpression expression = new ValueExpression(value:'value')

        expect:
        expression.expression.toString() == 'value'
    }
}
