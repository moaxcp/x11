package com.github.moaxcp.x11protocol.xcbparser

class XUnitPadAlign implements XUnit {
    int align
    XResult result
    XCaseInfo caseInfo
    XBitcaseInfo bitcaseInfo

    @Override
    List<JavaPadAlign> getJavaUnit(JavaType javaType) {
        return [new JavaPadAlign(javaType: javaType, xUnit: this, align: align)]
    }
}