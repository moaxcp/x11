package com.github.moaxcp.x11protocol.parser


import groovy.util.slurpersupport.Node

import static java.util.Objects.requireNonNull
/**
 * A field in the x11 protocol.
 */
class XUnitField implements XUnit {
    final XResult result
    final String name
    final String type
    final String enumType
    final String altEnumType
    final String maskType
    final String altMaskType

    XUnitField(Map map) {
        result = map.result
        name = map.name
        type = map.type
        enumType = map.enumType
        altEnumType = map.altEnumType
        maskType = map.maskType
        altMaskType = map.altMaskType
    }

    XUnitField(XResult result, Node node) {
        this.result = result
        name = requireNonNull(node.attributes().get('name'), 'name must not be null')
        type = requireNonNull(node.attributes().get('type'), 'type must not be null')
        enumType = node.attributes().get('enum')
        altEnumType = node.attributes().get('altenum')
        maskType = node.attributes().get('mask')
        altMaskType = node.attributes().get('altmask')
    }

    static XUnitField xUnitField(XResult result, Node node) {
        return new XUnitField(result, node)
    }

    XType getResolvedType() {
        return result.resolveXType(type)
    }

    XTypeEnum getResolvedEnumType() {
        requireNonNull(enumType, "enumType must not be null")
        return (XTypeEnum) result.resolveXType(enumType)
    }

    XType getResolvedMaskType() {
        requireNonNull(maskType, "maskType must not be null")
        return result.resolveXType(maskType)
    }

    @Override
    JavaProperty getJavaUnit(JavaType javaType) {
        JavaProperty unit
        if(enumType) {
            unit = resolvedEnumType.getJavaProperty(javaType, this)
        } else {
            unit = resolvedType.getJavaProperty(javaType, this)
        }
        return unit
    }
}