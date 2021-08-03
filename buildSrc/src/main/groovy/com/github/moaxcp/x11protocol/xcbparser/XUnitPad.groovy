package com.github.moaxcp.x11protocol.xcbparser

class XUnitPad implements XUnit {
    int bytes
    XResult result
    XCaseInfo caseInfo
    XBitcaseInfo bitcaseInfo

    @Override
    List<JavaPad> getJavaUnit(JavaType javaType) {
        JavaPad javaPad = new JavaPad(javaType: javaType, x11Field: this, bytes:bytes)
        if(javaType.getXUnitSubtype().isPresent() && !caseInfo) {
            javaPad.readParam = true
        }
        return [javaPad]
    }
}
