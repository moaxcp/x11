package com.github.moaxcp.x11protocol.parser

import spock.lang.Specification

class AddExpressionSpec extends Specification {
    def 'two values'() {
        given:
        AddExpression expression = new AddExpression(expressions:[new ValueExpression(value:5), new ValueExpression(value:4)])

        expect:
        expression.expression == '5 + 4'
    }

    def 'nested subtract expression'() {
        given:
        AddExpression expression = new AddExpression(expressions:
            [new ValueExpression(value:7),
                new SubtractExpression(expressions:
                [new ValueExpression(value:5), new ValueExpression(value:4)])])

        expect:
        expression.expression == '7 + 5 - 4'
    }

    def 'nested subtract and multiply expression'() {
        given:
        AddExpression expression = new AddExpression(expressions: [
            new ValueExpression(value:7),
            new MultiplyExpression(expressions:[
                new ValueExpression(value:8),
                new FieldRefExpression(name:'a')]),
            new SubtractExpression(expressions: [
                new ValueExpression(value:5),
                new ValueExpression(value:4)])])

        expect:
        expression.expression == '7 + 8 * a + 5 - 4'
    }
}
