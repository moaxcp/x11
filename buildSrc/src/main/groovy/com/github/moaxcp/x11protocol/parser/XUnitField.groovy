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
    final String constantValue
    final String enumType
    final String altEnumType
    final String maskType
    final String altMaskType
    final boolean localOnly
    final XBitcaseInfo bitcaseInfo

    XUnitField(Map map) {
        result = requireNonNull(map.result, 'result must not be null')
        name = requireNonNull(map.name, 'name must not be null')
        type = requireNonNull(map.type, 'type must not be null')
        constantValue = map.constantValue
        enumType = map.enumType
        altEnumType = map.altEnumType
        maskType = map.maskType
        altMaskType = map.altMaskType
        localOnly = map.localOnly ?: false
        bitcaseInfo = map.bitcaseInfo
    }

    XUnitField(XResult result, Node node) {
        this.result = requireNonNull(result, 'result must not be null')
        name = requireNonNull(node.attributes().get('name'), 'name must not be null')
        type = requireNonNull(node.attributes().get('type'), 'type must not be null')
        enumType = node.attributes().get('enum')
        altEnumType = node.attributes().get('altenum')
        maskType = node.attributes().get('mask')
        altMaskType = node.attributes().get('altmask')
    }

    XUnitField(XResult result, Node node, XBitcaseInfo bitcaseInfo) {
        this(result, node)
        this.bitcaseInfo = bitcaseInfo
    }

    static XUnitField xUnitField(XResult result, Node node) {
        return new XUnitField(result, node)
    }

    static XUnitField xUnitField(XResult result, Node node, XBitcaseInfo bitcaseInfo) {
        return new XUnitField(result, node, bitcaseInfo)
    }

    static XUnitField xUnitFieldFd(XResult result, Node node) {
        return new XUnitField(result: result, name: node.attributes().get('name'), type: 'INT32')
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
        return resolvedType.getJavaProperty(javaType, this)
    }
}