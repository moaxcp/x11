package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.ClassName

import static com.github.moaxcp.x11protocol.parser.XUnitField.xUnitField

class XUnitFieldSpec extends XmlSpec {
    def 'CARD32 field'() {
        given:
        xmlBuilder.field(name:'red_mask', type:'CARD32')
        JavaType javaType = Mock(JavaType)

        when:
        XUnitField field = xUnitField(result, getFirstNode())

        then:
        field.name == 'red_mask'
        field.type == 'CARD32'
        field.resolvedType.name == 'CARD32'
        field.getJavaUnit(javaType).name == 'redMask'
    }

    def 'enum field'() {
        given:
        xmlBuilder.field(name:'class', type:'CARD8', 'enum':'VisualClass')

        when:
        XUnitField field = xUnitField(result, getFirstNode())

        then:
        field.name == 'class'
        field.type == 'CARD8'
        field.resolvedType.name == 'CARD8'
    }

    def 'enum field resolve enum'() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            "enum"(name:'EventMask') {
                item(name:'NoEvent') {
                    value("0")
                }
                item(name:'KeyPress') {
                    bit('0')
                }
                item(name:'KeyRelease') {
                    bit('1')
                }
            }
        }

        when:
        addChildNodes()
        XUnitField field = new XUnitField(
            result:result,
            name:'mask',
            type:'CARD8',
            enumType: 'EventMask'
        )
        JavaType javaType = Mock(JavaType)

        then:
        field.name == 'mask'
        field.type == 'CARD8'
        field.resolvedEnumType.name == 'EventMask'
        field.getJavaUnit(javaType).typeName == ClassName.get('com.github.moaxcp.x11client.protocol.xproto', 'EventMaskEnum')
    }

    def 'mask field resolve mask'() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            "enum"(name:'EventMask') {
                item(name:'NoEvent') {
                    value("0")
                }
                item(name:'KeyPress') {
                    bit('0')
                }
                item(name:'KeyRelease') {
                    bit('1')
                }
            }
        }

        when:
        addChildNodes()
        XUnitField field = new XUnitField(
            result:result,
            name:'mask',
            type:'CARD8',
            maskType: 'EventMask'
        )

        then:
        field.name == 'mask'
        field.type == 'CARD8'
        field.resolvedMaskType.name == 'EventMask'
    }
}
