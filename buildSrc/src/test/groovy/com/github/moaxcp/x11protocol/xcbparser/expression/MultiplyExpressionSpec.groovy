package com.github.moaxcp.x11protocol.xcbparser.expression

import com.github.moaxcp.x11protocol.xcbparser.JavaPrimativeProperty
import com.github.moaxcp.x11protocol.xcbparser.JavaType
import com.github.moaxcp.x11protocol.xcbparser.XResult
import com.github.moaxcp.x11protocol.xcbparser.XUnitField
import spock.lang.Specification

class MultiplyExpressionSpec extends Specification {
    def 'two values'() {
        given:
        MultiplyExpression expression = new MultiplyExpression(expressions:[new ValueExpression(value:5), new ValueExpression(value:4)])

        expect:
        expression.expression.toString() == '5 * 4'
    }

    def 'nested addition'() {
        given:
        XResult xResult = new XResult()
        JavaType javaType = Mock(JavaType) {
            it.getXUnitSubtype() >> Optional.empty()
        }
        javaType.simpleName >> 'SimpleName'
        javaType.getJavaProperty(_) >> {
            new JavaPrimativeProperty(
                javaType,
                new XUnitField(result: xResult, name: it[0], type: 'CARD32')
            )
        }
        MultiplyExpression expression = new MultiplyExpression(expressions:[
            new FieldRefExpression(javaType:javaType, fieldName:'a'),
            new AddExpression(expressions:[
                new FieldRefExpression(javaType:javaType, fieldName:'b'),
                new FieldRefExpression(javaType:javaType, fieldName:'c')]),
            new FieldRefExpression(javaType:javaType, fieldName:'d')])

        expect:
        expression.expression.toString() == 'Integer.toUnsignedLong(a) * (Integer.toUnsignedLong(b) + Integer.toUnsignedLong(c)) * Integer.toUnsignedLong(d)'
    }

    def 'nested divide'() {
        given:
        XResult xResult = new XResult()
        JavaType javaType = Mock(JavaType) {
            it.getXUnitSubtype() >> Optional.empty()
        }
        javaType.simpleName >> 'SimpleName'
        javaType.getJavaProperty(_) >> {
            new JavaPrimativeProperty(
                javaType,
                new XUnitField(result: xResult, name: it[0], type: 'CARD32')
            )
        }
        MultiplyExpression expression = new MultiplyExpression(expressions:[
            new FieldRefExpression(javaType:javaType, fieldName:'a'),
            new DivideExpression(expressions:[
                new FieldRefExpression(javaType:javaType, fieldName:'b'),
                new FieldRefExpression(javaType:javaType, fieldName:'c')]),
            new FieldRefExpression(javaType:javaType, fieldName:'d')])

        expect:
        expression.expression.toString() == 'Integer.toUnsignedLong(a) * (Integer.toUnsignedLong(b) / Integer.toUnsignedLong(c)) * Integer.toUnsignedLong(d)'
    }
}
