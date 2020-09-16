package com.github.moaxcp.x11protocol.parser.expression

import com.github.moaxcp.x11protocol.parser.JavaPrimativeProperty
import com.github.moaxcp.x11protocol.parser.JavaType
import com.github.moaxcp.x11protocol.parser.XResult
import com.github.moaxcp.x11protocol.parser.XUnitField
import spock.lang.Specification

class DivideExpressionSpec extends Specification {
    def 'two values'() {
        given:
        DivideExpression expression = new DivideExpression(expressions:[new ValueExpression(value:5), new ValueExpression(value:4)])

        expect:
        expression.expression.toString() == '5 / 4'
    }

    def 'nested addition'() {
        given:
        XResult xResult = new XResult()
        JavaType javaType = Mock(JavaType)
        javaType.simpleName >> 'SimpleName'
        javaType.getField(_) >> {
            new JavaPrimativeProperty(
                javaType,
                new XUnitField(result: xResult, name: it[0], type: 'CARD32')
            )
        }
        DivideExpression expression = new DivideExpression(expressions:[
            new FieldRefExpression(javaType:javaType, fieldName:'a'),
            new AddExpression(expressions:[
                new FieldRefExpression(javaType:javaType, fieldName:'b'),
                new FieldRefExpression(javaType:javaType, fieldName:'c')]),
            new FieldRefExpression(javaType:javaType, fieldName:'d')])

        when:
        String result = expression.expression.toString()

        then:
        result == 'Integer.toUnsignedLong(a) / (Integer.toUnsignedLong(b) + Integer.toUnsignedLong(c)) / Integer.toUnsignedLong(d)'
    }

    def 'nested divide'() {
        given:
        XResult xResult = new XResult()
        JavaType javaType = Mock(JavaType)
        javaType.simpleName >> 'SimpleName'
        javaType.getField(_) >> {
            new JavaPrimativeProperty(
                javaType,
                new XUnitField(result: xResult, name: it[0], type: 'CARD32')
            )
        }
        DivideExpression expression = new DivideExpression(expressions:[
            new FieldRefExpression(javaType:javaType, fieldName:'a'),
            new DivideExpression(expressions:[
                new FieldRefExpression(javaType:javaType, fieldName:'b'),
                new FieldRefExpression(javaType:javaType, fieldName:'c')]),
            new FieldRefExpression(javaType:javaType, fieldName:'d')])

        expect:
        expression.expression.toString() == 'Integer.toUnsignedLong(a) / (Integer.toUnsignedLong(b) / Integer.toUnsignedLong(c)) / Integer.toUnsignedLong(d)'
    }
}
