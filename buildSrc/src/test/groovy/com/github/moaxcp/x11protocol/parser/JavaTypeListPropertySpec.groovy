package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec
import com.github.moaxcp.x11protocol.parser.expression.ValueExpression
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName

import static com.github.moaxcp.x11protocol.parser.JavaTypeListProperty.javaTypeListProperty

class JavaTypeListPropertySpec extends XmlSpec {
    def create() {
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
        XUnitListField field = new XUnitListField(
            result:result,
            name: 'formats',
            type: 'FORMAT',
            lengthExpression: new ValueExpression(value: '20')
        )

        when:
        JavaTypeListProperty property = javaTypeListProperty(field)

        then:
        property.name == 'formats'
        property.baseTypeName == ClassName.get(field.result.javaPackage, 'FormatStruct')
        property.typeName == ParameterizedTypeName.get(ClassName.get(List), property.baseTypeName)
        property.lengthExpression.expression == '20'
        property.readCode.toString() == '''\
            java.util.List<com.github.moaxcp.x11client.protocol.xproto.FormatStruct> formats = new ArrayList<>();
            for(int i = 0; i < 20; i++) {
              formats.add(FormatStruct.readFormatStruct(in));
            }
        '''.stripIndent()
        property.writeCode.toString() == '''\
            for(com.github.moaxcp.x11client.protocol.xproto.FormatStruct t : formats) {
              t.writeFormatStruct(out);
            }
        '''.stripIndent()
    }
}
