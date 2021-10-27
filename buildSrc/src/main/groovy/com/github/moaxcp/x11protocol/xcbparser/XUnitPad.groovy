package com.github.moaxcp.x11protocol.xcbparser

class XUnitPad implements XUnit {
    int bytes
    XResult result
    XCaseInfo caseInfo
    XBitcaseInfo bitcaseInfo

    @Override
    List<JavaPad> getJavaUnit(JavaClass javaClass) {
        JavaPad javaPad = new JavaPad(javaClass: javaClass, x11Field: this, bytes:bytes)
        if(javaClass.getXUnitSubtype().isPresent() && !caseInfo) {
            javaPad.readParam = true
        }
        return [javaPad]
    }
}
