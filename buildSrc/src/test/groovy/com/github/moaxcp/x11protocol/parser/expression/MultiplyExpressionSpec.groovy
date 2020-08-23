package com.github.moaxcp.x11protocol.parser.expression

import com.github.moaxcp.x11protocol.parser.JavaPrimativeProperty
import com.github.moaxcp.x11protocol.parser.JavaType
import com.squareup.javapoet.TypeName
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
        JavaType javaType = Mock(JavaType)
        javaType.simpleName >> 'SimpleName'
        javaType.getField(_) >> {
            new JavaPrimativeProperty(
                name: it[0],
                x11Primative: 'CARD32',
                memberTypeName: TypeName.INT
            )
        }
        MultiplyExpression expression = new MultiplyExpression(expressions:[
            new FieldRefExpression(javaType:javaType, fieldName:'a'),
            new AddExpression(expressions:[
                new FieldRefExpression(javaType:javaType, fieldName:'b'),
                new FieldRefExpression(javaType:javaType, fieldName:'c')]),
            new FieldRefExpression(javaType:javaType, fieldName:'d')])

        expect:
        expression.expression.toString() == 'a * (b + c) * d'
    }

    def 'nested divide'() {
        given:
        JavaType javaType = Mock(JavaType)
        javaType.simpleName >> 'SimpleName'
        javaType.getField(_) >> {
            new JavaPrimativeProperty(
                name: it[0],
                x11Primative: 'CARD32',
                memberTypeName: TypeName.INT
            )
        }
        MultiplyExpression expression = new MultiplyExpression(expressions:[
            new FieldRefExpression(javaType:javaType, fieldName:'a'),
            new DivideExpression(expressions:[
                new FieldRefExpression(javaType:javaType, fieldName:'b'),
                new FieldRefExpression(javaType:javaType, fieldName:'c')]),
            new FieldRefExpression(javaType:javaType, fieldName:'d')])

        expect:
        expression.expression.toString() == 'a * (b / c) * d'
    }
}
