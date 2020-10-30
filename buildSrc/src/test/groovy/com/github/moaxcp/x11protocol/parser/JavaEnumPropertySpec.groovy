package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.parser.JavaEnumProperty.javaEnumProperty

class JavaEnumPropertySpec extends XmlSpec {
    def create() {
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
        XUnitField field = new XUnitField(result: result, name: 'mask', type: 'CARD32', enumType: 'EventMask')

        when:
        JavaType javaType = Mock(JavaType)
        JavaEnumProperty property = javaEnumProperty(javaType, field)

        then:
        property.name == 'mask'
        property.x11Type == 'CARD32'
        property.memberTypeName == ClassName.get(result.javaPackage, 'EventMaskEnum')
        property.typeName == ClassName.get(result.javaPackage, 'EventMaskEnum')
        property.ioTypeName == TypeName.INT
        property.member.toString() == '''\
                @lombok.NonNull
                private com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum mask;
            '''.stripIndent()
        property.readCode.toString() == 'com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum mask = com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum.getByCode(in.readCard32());\n'
        property.writeCode.toString() == 'out.writeCard32(mask.getValue());\n'
    }
}
