package com.github.moaxcp.x11protocol.xcbparser.expression

import com.github.moaxcp.x11protocol.xcbparser.JavaPrimativeProperty
import com.github.moaxcp.x11protocol.xcbparser.JavaType
import com.github.moaxcp.x11protocol.xcbparser.XResult
import com.github.moaxcp.x11protocol.xcbparser.XUnitField
import spock.lang.Specification

class FieldRefExpressionSpec extends Specification {

    JavaType javaType = Stub(JavaType.class)

    def 'name'() {
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
        FieldRefExpression expression = new FieldRefExpression(javaType: javaType, fieldName:'name')

        expect:
        expression.getExpression().toString() == 'Integer.toUnsignedLong(name)'
    }
}
