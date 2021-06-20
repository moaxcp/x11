package com.github.moaxcp.x11protocol.xcbparser

import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.xcbparser.XUnitField.xUnitField
import static com.github.moaxcp.x11protocol.xcbparser.XUnitListField.xUnitListField
import static com.github.moaxcp.x11protocol.xcbparser.XUnitPadFactory.xUnitPad

class XUnitSwitchCase extends XUnitSwitch {
    String fieldRef
    List<XUnit> fields

    @Override
    List<JavaUnit> getJavaUnit(JavaType javaType) {
        return fields.inject([]) { List<JavaUnit> units, XUnit unit ->
            if(!unit.caseInfo) {
                units.addAll(unit.getJavaUnit(javaType))
            }
            if(javaType.simpleName.endsWith(unit.caseInfo.caseName.capitalize())) {
                units.addAll(unit.getJavaUnit(javaType))
            }
            return units
        }.flatten()
    }

    static XUnitSwitchCase parseCases(XResult result, Node node) {
        String fieldRef = node.childNodes().find{Node it -> it.name() == 'fieldref'}.text()
        List<XUnit> fields = []
        node.childNodes().each { Node switchNode ->
            if(switchNode.name() == 'required_start_align') {

            } else if(switchNode.name() == 'case') {
                String caseName = switchNode.attributes().get('name')
                String enumRef
                String enumItem
                switchNode.childNodes().each { Node caseNode ->
                    if(caseNode.name() == 'enumref') {
                        enumRef = caseNode.attributes().get('ref')
                        enumItem = caseNode.text()
                    } else {
                        XUnit unit = parseXUnit(result, caseNode, new XCaseInfo(caseField: fieldRef, caseName: caseName, enumType: enumRef, enumItem: enumItem))
                        if(unit) {
                            fields.add(unit)
                        }
                    }
                }
            }
        }
        return new XUnitSwitchCase(fieldRef: fieldRef, fields: fields)
    }

    static XUnit parseXUnit(XResult result, Node node, XCaseInfo caseInfo) {
        switch(node.name()) {
            case 'required_start_align':
                return null
            case 'pad':
                return xUnitPad(result, node, caseInfo)
            case 'field':
                return xUnitField(result, node, caseInfo)
            case 'list':
                return xUnitListField(result, node, caseInfo)
            default:
                throw new IllegalArgumentException("cannot parse ${node.name()}")
        }
    }
}
