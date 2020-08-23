package com.github.moaxcp.x11protocol.parser


import groovy.util.slurpersupport.Node

class XUnitListField extends XUnitField {
    Node lengthExpression

    static XUnitListField xUnitListField(XResult result, Node node) {
        String fieldName = node.attributes().get('name')
        String fieldType = node.attributes().get('type')
        String fieldEnum = node.attributes().get('enum')
        String fieldAltEnum = node.attributes().get('altenum')
        String fieldMask = node.attributes().get('mask')
        String fieldAltMask = node.attributes().get('altmask')
        XUnitListField field = new XUnitListField(
            result:result,
            name:fieldName,
            type:fieldType,
            enumType:fieldEnum,
            altEnumType: fieldAltEnum,
            maskType:fieldMask,
            altMaskType: fieldAltMask,
            lengthExpression: (Node) node.childNodes().next()
        )
        return field
    }

    @Override
    JavaListProperty getJavaUnit(JavaType javaType) {
        if(enumType) {
            return resolvedEnumType.getJavaListProperty(javaType, this)
        }
        return resolvedType.getJavaListProperty(javaType, this)
    }
}
