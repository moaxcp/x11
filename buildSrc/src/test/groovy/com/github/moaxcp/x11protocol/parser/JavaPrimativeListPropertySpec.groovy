package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec
import com.github.moaxcp.x11protocol.parser.expression.FieldRefExpression
import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.parser.JavaPrimativeListProperty.javaPrimativeListProperty

class JavaPrimativeListPropertySpec extends XmlSpec {
    def create() {
        given:
        XUnitListField field = new XUnitListField(
            result:result,
            name:'window_modifiers',
            type:'CARD64',
            lengthExpression: new FieldRefExpression(fieldName: 'num_window_modifiers')
        )

        when:
        JavaPrimativeListProperty property = javaPrimativeListProperty(field)

        then:
        property.name == 'windowModifiers'
        property.x11Primative == 'CARD64'
        property.baseTypeName == TypeName.LONG
        property.typeName == ArrayTypeName.of(TypeName.LONG)
        property.lengthExpression.expression == 'numWindowModifiers'
        property.readCode.toString() == 'long[] windowModifiers = in.readCard64(numWindowModifiers)'
        property.writeCode.toString() == 'out.writeCard64(windowModifiers)'
    }
}
