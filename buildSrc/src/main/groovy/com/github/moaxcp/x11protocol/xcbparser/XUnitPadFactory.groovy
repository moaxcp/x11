package com.github.moaxcp.x11protocol.xcbparser

import groovy.util.slurpersupport.Node

class XUnitPadFactory {
    static XUnit xUnitPad(Node node) {
        String bytes = node.attributes().get('bytes')
        String align = node.attributes().get('align')
        if(bytes) {
            return new XUnitPad(bytes:Integer.valueOf(bytes))
        }
        if(align) {
            return new XUnitPadAlign(align:Integer.valueOf(align))
        }
    }

    static XUnit xUnitPad(XResult result, Node node, XCaseInfo caseInfo) {
        String bytes = node.attributes().get('bytes')
        String align = node.attributes().get('align')
        if(bytes) {
            return new XUnitPad(result: result, caseInfo: caseInfo, bytes:Integer.valueOf(bytes))
        }
        if(align) {
            return new XUnitPadAlign(result: result, caseInfo: caseInfo, align:Integer.valueOf(align))
        }
    }
}
