package com.github.moaxcp.x11protocol.xcbparser.expression

import com.github.moaxcp.x11protocol.xcbparser.JavaClass
import com.github.moaxcp.x11protocol.xcbparser.JavaPrimitiveProperty
import com.github.moaxcp.x11protocol.xcbparser.JavaType
import com.github.moaxcp.x11protocol.xcbparser.XResult
import com.github.moaxcp.x11protocol.xcbparser.XUnitField
import com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression
import spock.lang.Specification

class FieldRefExpressionSpec extends Specification {

    JavaType javaType = Stub(JavaType.class)

    def 'name'() {
        given:
        XResult xResult = new XResult()
        JavaClass javaClass = Mock(JavaClass) {
            it.getXUnitSubtype() >> Optional.empty()
        }
        javaClass.simpleName >> 'SimpleName'
        javaClass.getJavaProperty(_) >> {
            new JavaPrimitiveProperty(
                javaClass,
                new XUnitField(result: xResult, name: it[0], type: 'CARD32')
            )
        }
        FieldRefExpression expression = new FieldRefExpression(javaType: javaClass, fieldName:'name')

        expect:
        expression.getExpression().toString() == 'Integer.toUnsignedLong(name)'
    }
}
