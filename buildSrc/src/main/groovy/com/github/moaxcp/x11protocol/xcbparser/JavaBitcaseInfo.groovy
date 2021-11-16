package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.generator.Conventions
import com.github.moaxcp.x11protocol.xcbparser.expression.Expression
import com.github.moaxcp.x11protocol.xcbparser.expression.Expressions
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

class JavaBitcaseInfo {
    Expression maskField
    Set<JavaEnumRef> enumRefs

    JavaBitcaseInfo(XResult result, JavaType javaType, XBitcaseInfo bitcaseInfo) {
        maskField = Expressions.getExpression(javaType, bitcaseInfo.expression)
        enumRefs = bitcaseInfo.enumRefs.collect {
            new JavaEnumRef(enumType: result.resolveXType(it.enumType).javaType.className, enumItem: Conventions.getEnumValueName(it.enumItem))
        }
    }

    CodeBlock getExpression() {
        CodeBlock.join(enumRefs.collect {
            CodeBlock.of('$T.$L.isEnabled($L)', it.enumType, it.enumItem, maskField.getExpression(TypeName.INT))
        }, ' && ')
    }
}
