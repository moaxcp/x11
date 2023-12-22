package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.xcbparser.JavaEnumListProperty.javaEnumListProperty

class JavaEnumListPropertySpec extends XmlSpec {
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
        XUnitListField field = new XUnitListField(
            result: result,
            name: 'masks',
            type: 'CARD32',
            enumType: 'EventMask',
            lengthExpression: new XmlSlurper().parseText('<value>20</value>').nodeIterator().next()
        )
        JavaClass javaClass = Mock(JavaClass) {
            it.getXUnitSubtype() >> Optional.empty()
        }

        when:
        JavaEnumListProperty property = javaEnumListProperty(javaClass, field)
        CodeBlock.Builder writeCode = CodeBlock.builder()
        property.addWriteCode(writeCode)

        then:
        property.name == 'masks'
        property.x11Type == 'CARD32'
        property.baseTypeName == ClassName.get(field.result.javaPackage, 'EventMask')
        property.typeName == ParameterizedTypeName.get(ClassName.get(List), property.baseTypeName)
        property.ioTypeName == TypeName.INT
        property.lengthExpression.expression.toString() == '20'
        property.declareAndReadCode.toString() == '''\
            java.util.List<com.github.moaxcp.x11client.protocol.xproto.EventMask> masks = new java.util.ArrayList<>(20);
            for(int i = 0; i < 20; i++) {
              masks.add(com.github.moaxcp.x11client.protocol.xproto.EventMask.getByCode(in.readCard32()));
            }
            '''.stripIndent()
        writeCode.build().toString() == '''\
            for(com.github.moaxcp.x11client.protocol.xproto.EventMask e : masks) {
              out.writeCard32(e.getValue());
            }
            '''.stripIndent()
    }
}
