package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.ClassName

import static com.github.moaxcp.x11protocol.parser.JavaTypeProperty.javaTypeProperty

class JavaTypePropertySpec extends XmlSpec {
    def 'create'() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            struct(name:'FORMAT') {
                field(type:'CARD8', name:'depth')
                field(type:'CARD8', name:'bits_per_pixel')
                field(type:'CARD8', name:'scanline_pad')
                pad(bytes:5)
            }
        }
        addChildNodes()
        XUnitField field = new XUnitField(result: result, name: 'format', type:'FORMAT')
        JavaType javaType = Mock(JavaType)

        when:
        JavaTypeProperty property = javaTypeProperty(javaType, field)

        then:
        property.name == 'format'
        property.typeName == ClassName.get(result.javaPackage, 'FormatStruct')
        property.readCode.toString() == 'com.github.moaxcp.x11client.protocol.xproto.FormatStruct format = com.github.moaxcp.x11client.protocol.xproto.FormatStruct.readFormatStruct(in);\n'
        property.writeCode.toString() == 'format.write(out);\n'
    }
}
