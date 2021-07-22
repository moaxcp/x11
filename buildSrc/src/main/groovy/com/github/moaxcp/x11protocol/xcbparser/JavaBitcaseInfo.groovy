package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.generator.Conventions
import com.github.moaxcp.x11protocol.xcbparser.expression.Expression
import com.github.moaxcp.x11protocol.xcbparser.expression.Expressions
import com.squareup.javapoet.ClassName

class JavaBitcaseInfo {
    Expression maskField
    ClassName enumType
    String enumItem

    JavaBitcaseInfo(XResult result, JavaType javaType, XBitcaseInfo bitcaseInfo) {
        maskField = Expressions.getExpression(javaType, bitcaseInfo.expression)
        enumItem = Conventions.getEnumValueName(bitcaseInfo.enumItem)
        enumType = result.resolveXType(bitcaseInfo.enumType).javaType.className
    }
}
