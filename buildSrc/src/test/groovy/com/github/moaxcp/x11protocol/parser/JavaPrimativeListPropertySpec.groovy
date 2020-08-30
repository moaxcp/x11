package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.parser.JavaPrimativeListProperty.javaPrimativeListProperty

class JavaPrimativeListPropertySpec extends XmlSpec {
    def create() {
        given:
        XUnitListField field = new XUnitListField(
            result:result,
            name:'window_modifiers',
            type:'CARD64',
            lengthExpression: new XmlSlurper().parseText('<fieldref>num_window_modifiers</fieldref>').nodeIterator().next()
        )
        JavaType javaType = Mock(JavaType)
        javaType.simpleName >> 'SimpleName'
        javaType.getField(_) >> {
            new JavaPrimativeProperty(
                name: it[0],
                x11Primative: 'CARD64',
                memberTypeName: TypeName.LONG
            )
        }

        when:
        JavaPrimativeListProperty property = javaPrimativeListProperty(javaType, field)

        then:
        property.name == 'windowModifiers'
        property.x11Primative == 'CARD64'
        property.baseTypeName == TypeName.LONG
        property.typeName == ArrayTypeName.of(TypeName.LONG)
        property.lengthExpression.expression.toString() == 'numWindowModifiers'
        property.readCode.toString() == 'long[] windowModifiers = in.readCard64(numWindowModifiers);\n'
        property.writeCode.toString() == 'out.writeCard64(windowModifiers);\n'
    }
}
