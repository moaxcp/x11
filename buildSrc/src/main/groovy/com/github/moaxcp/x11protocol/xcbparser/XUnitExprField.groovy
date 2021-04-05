package com.github.moaxcp.x11protocol.xcbparser

import groovy.util.slurpersupport.Node

class XUnitExprField extends XUnitField {
    final Node expression

    XUnitExprField(Map map) {
        super(map)
        expression = map.expression
    }

    XUnitExprField(XResult result, Node node) {
        super(result, node)
        expression = (Node) node.childNodes().next()
    }

    static XUnitExprField xUnitExprField(XResult result, Node node) {
        return new XUnitExprField(result, node)
    }

    @Override
    JavaProperty getJavaUnit(JavaType javaType) {
        if(resolvedType.name != 'BOOL') {
            throw new UnsupportedOperationException(resolvedType.name + ' not supported')
        }
        return new JavaExprProperty(javaType, this)
    }
}
