package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import spock.lang.Ignore

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
            lengthExpression: new XmlSlurper().parseText('<value>20</value>').nodeIterator().next()
        )
        JavaType javaType = Mock(JavaType)

        when:
        JavaTypeListProperty property = javaTypeListProperty(javaType, field)

        then:
        property.name == 'formats'
        property.baseTypeName == ClassName.get(field.result.javaPackage, 'FormatStruct')
        property.typeName == ParameterizedTypeName.get(ClassName.get(List), property.baseTypeName)
        property.lengthExpression.expression.toString() == '20'
        property.readCode.toString() == '''\
            java.util.List<com.github.moaxcp.x11client.protocol.xproto.FormatStruct> formats = new java.util.ArrayList<>();
            for(int i = 0; i < 20; i++) {
              formats.add(FormatStruct.readFormatStruct(in));
            }
        '''.stripIndent()
        property.writeCode.toString() == '''\
            for(com.github.moaxcp.x11client.protocol.xproto.FormatStruct t : formats) {
              t.write(out);
            }
        '''.stripIndent()
    }

    @Ignore
    def 'no expression'() {
        /*
        a list may not have an expression. In this case the object has
        a specific size and the list takes the rest of the bytes left
         */
        given:
        xmlBuilder.xcb(header:'present') {
            struct(name:'Notify') {
                field(type:'CARD32', name:'serial')
            }
        }
        addChildNodes()
        XUnitListField field = new XUnitListField(
            result:result,
            name: 'notifies',
            type: 'Notify'
        )
        JavaType javaType = Mock(JavaType)

        when:
        JavaTypeListProperty property = javaTypeListProperty(javaType, field)

        then:
        property.name == 'formats'
        property.baseTypeName == ClassName.get(field.result.javaPackage, 'FormatStruct')
        property.typeName == ParameterizedTypeName.get(ClassName.get(List), property.baseTypeName)
        property.lengthExpression.expression.toString() == '20'
        property.readCode.toString() == '''\
            java.util.List<com.github.moaxcp.x11client.protocol.xproto.FormatStruct> formats = new java.util.ArrayList<>();
            for(int i = 0; i < 20; i++) {
              formats.add(FormatStruct.readFormatStruct(in));
            }
        '''.stripIndent()
        property.writeCode.toString() == '''\
            for(com.github.moaxcp.x11client.protocol.xproto.FormatStruct t : formats) {
              t.write(out);
            }
        '''.stripIndent()
    }
}
