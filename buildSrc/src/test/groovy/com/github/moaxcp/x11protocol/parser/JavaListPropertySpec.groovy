package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.ValueExpression
import com.squareup.javapoet.TypeName
import spock.lang.Specification

import static com.github.moaxcp.x11protocol.parser.JavaPrimativeListProperty.javaPrimativeListProperty

class JavaListPropertySpec extends Specification {
    def 'create'() {
        given:
        XResult result = new XResult()
        XUnitListField field = new XUnitListField(
            result: result,
            name: 'visuals',
            type: 'CARD32',
            lengthExpression: new ValueExpression(value:'5')
        )

        when:
        JavaListProperty property = javaPrimativeListProperty(field)

        then:
        property.name == 'visuals'
        property.x11Primative == 'CARD32'
        property.baseType == TypeName.INT
        property.lengthExpression.expression == '5'
    }
}
