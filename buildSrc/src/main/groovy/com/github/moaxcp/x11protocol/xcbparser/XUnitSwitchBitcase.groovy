package com.github.moaxcp.x11protocol.xcbparser

import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.xcbparser.XTypeObject.parseXUnit

class XUnitSwitchBitcase extends XUnitSwitch {
    Node expression
    List<XUnit> fields

    @Override
    List<JavaUnit> getJavaUnit(JavaType javaType) {
        return fields.collect {
            it.getJavaUnit(javaType)
        }.flatten()
    }

    static XUnitSwitchBitcase parseValueList(XResult result, Node node) {
        Node expression = node.childNodes()getAt(0) //expression is first node
        List<XUnit> fields = []
        node.childNodes().each { Node it ->
            if(it.name() == 'fieldref') {
                return
            }
            if(it.name() == 'bitcase') {
                String enumRef
                String enumItem
                it.childNodes().each { Node bitcaseNode ->
                    if(bitcaseNode.name() == 'enumref') {
                        enumRef = bitcaseNode.attributes().get('ref')
                        enumItem = bitcaseNode.text()
                    } else {
                        XUnit unit = parseXUnit(result, bitcaseNode, new XBitcaseInfo(expression: expression, enumType: enumRef, enumItem: enumItem))
                        fields.add(unit)
                    }
                }
            }
        }
        return new XUnitSwitchBitcase(expression: expression, fields: fields)
    }
}
