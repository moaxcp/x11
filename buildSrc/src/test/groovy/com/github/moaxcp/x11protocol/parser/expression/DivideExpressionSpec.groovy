package com.github.moaxcp.x11protocol.parser.expression

import com.github.moaxcp.x11protocol.parser.expression.AddExpression
import com.github.moaxcp.x11protocol.parser.expression.DivideExpression
import com.github.moaxcp.x11protocol.parser.expression.FieldRefExpression
import com.github.moaxcp.x11protocol.parser.expression.ValueExpression
import spock.lang.Specification

class DivideExpressionSpec extends Specification {
    def 'two values'() {
        given:
        DivideExpression expression = new DivideExpression(expressions:[new ValueExpression(value:5), new ValueExpression(value:4)])

        expect:
        expression.expression == '5 / 4'
    }

    def 'nested addition'() {
        given:
        DivideExpression expression = new DivideExpression(expressions:[
            new FieldRefExpression(name:'a'),
            new AddExpression(expressions:[
                new FieldRefExpression(name:'b'),
                new FieldRefExpression(name:'c')]),
            new FieldRefExpression(name:'d')])

        expect:
        expression.expression == 'a / (b + c) / d'
    }

    def 'nested divide'() {
        given:
        DivideExpression expression = new DivideExpression(expressions:[
            new FieldRefExpression(name:'a'),
            new DivideExpression(expressions:[
                new FieldRefExpression(name:'b'),
                new FieldRefExpression(name:'c')]),
            new FieldRefExpression(name:'d')])

        expect:
        expression.expression == 'a / (b / c) / d'
    }
}
