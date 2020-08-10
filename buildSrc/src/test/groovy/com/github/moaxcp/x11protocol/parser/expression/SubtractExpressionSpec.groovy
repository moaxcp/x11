package com.github.moaxcp.x11protocol.parser.expression

import com.github.moaxcp.x11protocol.parser.expression.AddExpression
import com.github.moaxcp.x11protocol.parser.expression.SubtractExpression
import com.github.moaxcp.x11protocol.parser.expression.ValueExpression
import spock.lang.Specification

class SubtractExpressionSpec extends Specification {
    def 'two values'() {
        given:
        SubtractExpression expression = new SubtractExpression(expressions:[new ValueExpression(value:5), new ValueExpression(value:4)])

        expect:
        expression.expression.toString() == '5 - 4'
    }

    def 'nested add expression'() {
        given:
        SubtractExpression expression = new SubtractExpression(expressions:
            [new ValueExpression(value:7),
             new AddExpression(expressions:
                 [new ValueExpression(value:5), new ValueExpression(value:4)])])

        expect:
        expression.expression.toString() == '7 - 5 + 4'
    }
}
