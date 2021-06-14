package com.github.moaxcp.x11protocol.xcbparser

import groovy.util.slurpersupport.Node

class XUnitRequiredStartAlign implements XUnit {
    int align

    XCaseInfo getCaseInfo() {
        throw new UnsupportedOperationException("nested cases not supported")
    }

    static XUnitRequiredStartAlign xUnitRequiredStartAlign(XResult result, Node node) {
        return new XUnitRequiredStartAlign(align: Integer.valueOf((String) node.attributes().get('align')))
    }

    @Override
    List<JavaRequiredStartAlign> getJavaUnit(JavaType javaType) {
        return [new JavaRequiredStartAlign(javaType: javaType, xUnit: this, align:align)]
    }
}
