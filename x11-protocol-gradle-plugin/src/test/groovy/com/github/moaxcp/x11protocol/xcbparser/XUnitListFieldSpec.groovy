package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec
import com.github.moaxcp.x11protocol.xcbparser.XUnitListField

class XUnitListFieldSpec extends XmlSpec {

    def 'CARD32 list'() {
        given:
        xmlBuilder.list(type:'CARD32', name:'visuals') {
            op(op:'*') {
                fieldref('visuals_len')
                value('4')
            }
        }

        when:
        XUnitListField field = XUnitListField.xUnitListField(result, getFirstNode())

        then:
        field.type == 'CARD32'
        field.name == 'visuals'
        field.resolvedType.name == 'CARD32'
    }

    def 'String list'() {
        given:
        xmlBuilder.list(type:'char', name:'list')

        when:
        XUnitListField field = XUnitListField.xUnitListField(result, getFirstNode())

        then:
        field.type == 'char'
        field.name == 'list'
        field.lengthExpression == null
        field.resolvedType.name == 'char'
    }
}
