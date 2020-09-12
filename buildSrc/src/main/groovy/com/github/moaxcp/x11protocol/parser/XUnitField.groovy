package com.github.moaxcp.x11protocol.parser

import groovy.util.slurpersupport.Node

import static java.util.Objects.requireNonNull
/**
 *
 */
class XUnitField implements XUnit {
    XResult result
    String name
    String type
    String enumType
    String altEnumType
    String maskType
    String altMaskType

    static XUnitField xUnitField(XResult result, Node node) {
        String fieldName = node.attributes().get('name')
        String fieldType = node.attributes().get('type')
        String fieldEnum = node.attributes().get('enum')
        String fieldAltEnum = node.attributes().get('altenum')
        String fieldMask = node.attributes().get('mask')
        String fieldAltMask = node.attributes().get('altmask')
        return new XUnitField(
            result:result,
            name:fieldName,
            type:fieldType,
            enumType:fieldEnum,
            altEnumType: fieldAltEnum,
            maskType:fieldMask,
            altMaskType: fieldAltMask
        )
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
            unit = resolvedEnumType.getJavaProperty(this)
        } else {
            unit = resolvedType.getJavaProperty(this)
        }
        return unit
    }
}