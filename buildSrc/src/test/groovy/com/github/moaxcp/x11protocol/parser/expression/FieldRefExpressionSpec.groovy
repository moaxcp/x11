package com.github.moaxcp.x11protocol.parser.expression

import com.github.moaxcp.x11protocol.parser.JavaPrimativeProperty
import com.github.moaxcp.x11protocol.parser.JavaType
import com.github.moaxcp.x11protocol.parser.XResult
import com.github.moaxcp.x11protocol.parser.XUnitField
import spock.lang.Specification

class FieldRefExpressionSpec extends Specification {

    JavaType javaType = Stub(JavaType.class)

    def 'name'() {
        given:
        XResult xResult = new XResult()
        JavaType javaType = Mock(JavaType)
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
