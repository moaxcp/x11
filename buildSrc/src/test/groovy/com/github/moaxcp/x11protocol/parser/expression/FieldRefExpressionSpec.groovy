package com.github.moaxcp.x11protocol.parser.expression

import com.github.moaxcp.x11protocol.parser.JavaPrimativeProperty
import com.github.moaxcp.x11protocol.parser.JavaType
import com.squareup.javapoet.TypeName
import spock.lang.Specification

class FieldRefExpressionSpec extends Specification {

    JavaType javaType = Stub(JavaType.class)

    def 'name'() {
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
        FieldRefExpression expression = new FieldRefExpression(javaType: javaType, fieldName:'name')

        expect:
        expression.getExpression().toString() == 'name'
    }
}
