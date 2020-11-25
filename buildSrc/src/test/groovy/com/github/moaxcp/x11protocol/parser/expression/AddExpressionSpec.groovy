package com.github.moaxcp.x11protocol.parser.expression

import com.github.moaxcp.x11protocol.parser.JavaPrimativeProperty
import com.github.moaxcp.x11protocol.parser.JavaType
import com.github.moaxcp.x11protocol.parser.XResult
import com.github.moaxcp.x11protocol.parser.XUnitField
import spock.lang.Specification

class AddExpressionSpec extends Specification {
    def 'two values'() {
        given:
        AddExpression expression = new AddExpression(expressions:[new ValueExpression(value:5), new ValueExpression(value:4)])

        expect:
        expression.expression.toString() == '5 + 4'
    }

    def 'nested subtract expression'() {
        given:
        AddExpression expression = new AddExpression(expressions:
            [new ValueExpression(value:7),
                new SubtractExpression(expressions:
                [new ValueExpression(value:5), new ValueExpression(value:4)])])

        expect:
        expression.expression.toString() == '7 + 5 - 4'
    }

    def 'nested subtract and multiply expression'() {
        given:
        XResult xResult = new XResult()
        JavaType javaType = Mock(JavaType)
        javaType.getJavaProperty('a') >> new JavaPrimativeProperty(
            javaType,
            new XUnitField(result: xResult, name: 'a', type: 'CARD8')
        )
        AddExpression expression = new AddExpression(expressions: [
            new ValueExpression(value:7),
            new MultiplyExpression(expressions:[
                new ValueExpression(value:8),
                new FieldRefExpression(javaType: javaType, fieldName:'a')]),
            new SubtractExpression(expressions: [
                new ValueExpression(value:5),
                new ValueExpression(value:4)])])

        when:
        String result = expression.expression.toString()

        then:
        result == '7 + 8 * Byte.toUnsignedInt(a) + 5 - 4'
    }
}
