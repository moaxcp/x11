package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.TypeName

class JavaPrimativeListPropertySpec extends XmlSpec {
    def create() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            struct(name:'Window') {
                field(name: 'num_window_modifiers', type: 'CARD32')
                list(name: 'window_modifiers', type: 'CARD64') {
                    fieldref('num_window_modifiers')
                }
            }
        }
        addChildNodes()
        JavaObjectType javaType = result.resolveXType('Window').javaType

        when:
        JavaPrimativeListProperty property = javaType.protocol[1]

        then:
        property.name == 'windowModifiers'
        property.x11Type == 'CARD64'
        property.baseTypeName == TypeName.LONG
        property.typeName == ArrayTypeName.of(TypeName.LONG)
        property.lengthExpression.expression.toString() == 'Integer.toUnsignedLong(numWindowModifiers)'
        property.readCode.toString() == 'long[] windowModifiers = in.readCard64((int) (Integer.toUnsignedLong(numWindowModifiers)));\n'
        property.writeCode.toString() == 'out.writeCard64(windowModifiers);\n'
    }
}
