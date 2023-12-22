package com.github.moaxcp.x11protocol.xcbparser

import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.xcbparser.XTypeObject.parseXUnit

class XUnitSwitchBitcase extends XUnitSwitch {
    Node expression
    List<XUnit> fields

    @Override
    List<JavaUnit> getJavaUnit(JavaClass javaClass) {
        return fields.collect {
            it.getJavaUnit(javaClass)
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
                Set<XUnitEnumRef> enumRefs = []
                it.childNodes().each { Node bitcaseNode ->
                    if(bitcaseNode.name() == 'enumref') {
                        String enumType = bitcaseNode.attributes().get('ref')
                        String enumItem = bitcaseNode.text()
                        enumRefs.add(new XUnitEnumRef(enumType: enumType, enumItem: enumItem))
                    } else {
                        XUnit unit = parseXUnit(result, bitcaseNode, new XBitcaseInfo(expression: expression, enumRefs: enumRefs))
                        fields.add(unit)
                    }
                }
            }
        }
        return new XUnitSwitchBitcase(expression: expression, fields: fields)
    }
}
