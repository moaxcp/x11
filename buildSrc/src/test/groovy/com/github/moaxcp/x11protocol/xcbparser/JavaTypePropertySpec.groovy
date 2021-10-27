package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock

import static com.github.moaxcp.x11protocol.xcbparser.JavaTypeProperty.javaTypeProperty

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
        JavaClass javaClass = Mock(JavaClass) {
            it.getXUnitSubtype() >> Optional.empty()
        }

        when:
        JavaTypeProperty property = javaTypeProperty(javaClass, field)
        CodeBlock.Builder writeCode = CodeBlock.builder()
        property.addWriteCode(writeCode)

        then:
        property.name == 'format'
        property.typeName == ClassName.get(result.javaPackage, 'Format')
        property.declareAndReadCode.toString() == 'com.github.moaxcp.x11client.protocol.xproto.Format format = com.github.moaxcp.x11client.protocol.xproto.Format.readFormat(in);\n'
        writeCode.build().toString() == 'format.write(out);\n'
    }
}
