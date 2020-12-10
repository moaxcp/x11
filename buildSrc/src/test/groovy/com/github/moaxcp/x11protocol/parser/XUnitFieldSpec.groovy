package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.parser.XUnitField.xUnitField

class XUnitFieldSpec extends XmlSpec {

    def 'make with all fields'() {
        given:
        xmlBuilder.field(
            name:'field',
            type:'CARD32',
            enum:'Color',
            altenum:'AltColor',
            mask: 'Mask',
            altmask: 'AltMask'
        )

        when:
        XUnitField field = xUnitField(result, getFirstNode())

        then:
        field.result == result
        field.name == 'field'
        field.type == 'CARD32'
        field.enumType == 'Color'
        field.altEnumType == 'AltColor'
        field.maskType == 'Mask'
        field.altMaskType == 'AltMask'
    }

    def 'null name fails'() {
        given:
        xmlBuilder.field()

        when:
        xUnitField(result, getFirstNode())

        then:
        NullPointerException e = thrown()
        e.getMessage() == 'name must not be null'
    }

    def 'null type fails'() {
        given:
        xmlBuilder.field(name:'field')

        when:
        xUnitField(result, getFirstNode())

        then:
        NullPointerException e = thrown()
        e.getMessage() == 'type must not be null'
    }

    def 'type missing in result fails to resolve'() {
        given:
        xmlBuilder.field(name:'field', type:'Missing')

        when:
        xUnitField(result, getFirstNode()).resolvedType

        then:
        IllegalArgumentException e = thrown()
        e.getMessage() == 'could not resolve Missing'
    }

    def 'resolve primative type'() {
        given:
        xmlBuilder.field(name:'field', type:'CARD32')

        expect:
        xUnitField(result, getFirstNode()).resolvedType.name == 'CARD32'
    }

    def 'null enumType fails to resolve'() {
        given:
        xmlBuilder.field(name:'field', type:'CARD32')

        when:
        xUnitField(result, getFirstNode()).resolvedEnumType

        then:
        NullPointerException e = thrown()
        e.getMessage() == 'enumType must not be null'
    }

    def 'resolve enum type'() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            "enum"(name:'VisualClass') {
                item(name:'First') {
                    value("0")
                }
            }
        }
        addChildNodes()

        when:
        XUnitField field = new XUnitField(result: result, name: 'class', type: 'CARD8', enumType: 'VisualClass')

        then:
        field.name == 'class'
        field.type == 'CARD8'
        field.resolvedType.name == 'CARD8'
        field.resolvedEnumType.name == 'VisualClass'
    }

    def 'null maskType fails to resolve'() {
        given:
        xmlBuilder.field(name:'field', type:'CARD32')

        when:
        xUnitField(result, getFirstNode()).resolvedMaskType

        then:
        NullPointerException e = thrown()
        e.getMessage() == 'maskType must not be null'
    }

    def 'resolve mask type'() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            "enum"(name:'Mask') {
                item(name:'First') {
                    value("0")
                }
            }
        }
        addChildNodes()

        when:
        XUnitField field = new XUnitField(result: result, name: 'class', type: 'CARD8', maskType: 'Mask')

        then:
        field.name == 'class'
        field.type == 'CARD8'
        field.resolvedType.name == 'CARD8'
        field.resolvedMaskType.name == 'Mask'
    }

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
    }

    def 'convert to JavaUnit'() {
        given:
        xmlBuilder.field(name:'red_mask', type:'CARD32')
        JavaType javaType = Mock(JavaType)

        when:
        XUnitField field = xUnitField(result, getFirstNode())

        then:
        field.getJavaUnit(javaType).name == 'redMask'
    }

    def 'convert to JavaUnit with enumType'() {
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
        addChildNodes()

        when:
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
        field.getJavaUnit(javaType).typeName == TypeName.BYTE
    }
}
