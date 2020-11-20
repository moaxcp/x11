package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName

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
        property.baseTypeName == ClassName.get(Long.class)
        property.typeName == ParameterizedTypeName.get(ClassName.get(List.class), ClassName.get(Long.class))
        property.lengthExpression.expression.toString() == 'Integer.toUnsignedLong(numWindowModifiers)'
        property.declareAndReadCode.toString() == 'java.util.List<java.lang.Long> windowModifiers = in.readCard64((int) (Integer.toUnsignedLong(numWindowModifiers)));\n'
        property.writeCode.toString() == 'out.writeCard64(windowModifiers);\n'
    }
}
