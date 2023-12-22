package com.github.moaxcp.x11protocol.xcbparser

import groovy.util.slurpersupport.Node

class XUnitRequiredStartAlign implements XUnit {
    int align
    XBitcaseInfo bitcaseInfo
    XCaseInfo caseInfo

    static XUnitRequiredStartAlign xUnitRequiredStartAlign(XResult result, Node node) {
        return new XUnitRequiredStartAlign(align: Integer.valueOf((String) node.attributes().get('align')))
    }

    @Override
    List<JavaRequiredStartAlign> getJavaUnit(JavaClass javaClass) {
        return [new JavaRequiredStartAlign(javaClass: javaClass, xUnit: this, align:align)]
    }
}
