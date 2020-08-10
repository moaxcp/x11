package com.github.moaxcp.x11protocol.parser

import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.Node
import groovy.xml.MarkupBuilder
import spock.lang.Specification

class XListFieldSpec extends Specification {
    StringWriter writer = new StringWriter()
    MarkupBuilder xmlBuilder = new MarkupBuilder(writer)
    XResult result = new XResult(basePackage: 'com.github.moaxcp.x11client.protocol', header:'xproto')

    GPathResult getGPathResult() {
        new XmlSlurper().parseText(writer.toString())
    }

    Node getFirst() {
        (Node) getGPathResult().nodeIterator().next()
    }

    def 'CARD32 list'() {
        given:
        xmlBuilder.list(type:'CARD32', name:'visuals') {
            op(op:'*') {
                fieldref('visuals_len')
                value('4')
            }
        }

        when:
        XUnitListField field = XUnitListField.xUnitListField(result, getFirst())

        then:
        field.type == 'CARD32'
        field.name == 'visuals'
        field.lengthExpression.expression.toString() == 'visualsLen * 4'
        field.lengthField.fieldName == 'visuals_len'
        field.resolvedType.name == 'CARD32'
    }

    def 'String list'() {
        given:
        xmlBuilder.list(type:'char', name:'list')

        when:
        XUnitListField field = XUnitListField.xUnitListField(result, getFirst())

        then:
        field.type == 'char'
        field.name == 'list'
        field.lengthExpression == null
        field.lengthField == null
        field.resolvedType.name == 'char'
    }
}
