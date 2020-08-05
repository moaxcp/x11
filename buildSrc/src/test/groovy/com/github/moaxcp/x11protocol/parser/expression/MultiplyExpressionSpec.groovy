package com.github.moaxcp.x11protocol.parser.expression


import spock.lang.Specification

class MultiplyExpressionSpec extends Specification {
    def 'two values'() {
        given:
        MultiplyExpression expression = new MultiplyExpression(expressions:[new ValueExpression(value:5), new ValueExpression(value:4)])

        expect:
        expression.expression == '5 * 4'
    }

    def 'nested addition'() {
        given:
        MultiplyExpression expression = new MultiplyExpression(expressions:[
            new FieldRefExpression(fieldName:'a'),
            new AddExpression(expressions:[
                new FieldRefExpression(fieldName:'b'),
                new FieldRefExpression(fieldName:'c')]),
            new FieldRefExpression(fieldName:'d')])

        expect:
        expression.expression == 'a * (b + c) * d'
    }

    def 'nested divide'() {
        given:
        MultiplyExpression expression = new MultiplyExpression(expressions:[
            new FieldRefExpression(fieldName:'a'),
            new DivideExpression(expressions:[
                new FieldRefExpression(fieldName:'b'),
                new FieldRefExpression(fieldName:'c')]),
            new FieldRefExpression(fieldName:'d')])

        expect:
        expression.expression == 'a * (b / c) * d'
    }
}
