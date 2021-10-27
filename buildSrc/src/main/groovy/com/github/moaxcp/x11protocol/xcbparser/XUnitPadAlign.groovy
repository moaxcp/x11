package com.github.moaxcp.x11protocol.xcbparser

class XUnitPadAlign implements XUnit {
    int align
    XResult result
    XCaseInfo caseInfo
    XBitcaseInfo bitcaseInfo

    @Override
    List<JavaPadAlign> getJavaUnit(JavaClass javaClass) {
        return [new JavaPadAlign(javaClass: javaClass, xUnit: this, align: align)]
    }
}