package com.github.moaxcp.x11protocol.parser


import groovy.util.slurpersupport.Node
/**
 * A list in the x11 protocol.
 */
class XUnitListField extends XUnitField {
    final Node lengthExpression

    XUnitListField(Map map) {
        super(map)
        lengthExpression = map.lengthExpression
    }

    XUnitListField(XResult result, Node node) {
        super(result, node)
        lengthExpression = (Node) node.childNodes().next()
    }

    static XUnitListField xUnitListField(XResult result, Node node) {
        return new XUnitListField(result, node)
    }

    @Override
    JavaListProperty getJavaUnit(JavaType javaType) {
        if(enumType) {
            return resolvedEnumType.getJavaProperty(javaType, this)
        }
        return resolvedType.getJavaProperty(javaType, this)
    }
}
