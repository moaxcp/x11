package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.generator.Conventions
import com.squareup.javapoet.ClassName

class JavaBitcaseInfo {
    String maskField
    ClassName enumType
    String enumItem

    JavaBitcaseInfo(XResult result, XBitcaseInfo bitcaseInfo) {
        maskField = Conventions.convertX11VariableNameToJava(bitcaseInfo.maskField)
        enumItem = Conventions.getEnumValueName(bitcaseInfo.enumItem)
        enumType = result.resolveXType(bitcaseInfo.enumType).javaType.className
    }
}
