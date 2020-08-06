package com.github.moaxcp.x11protocol.parser

import groovy.util.slurpersupport.Node

class XUnitPad implements XUnit {
    int bytes

    static XUnitPad getXPad(Node node) {
        int padBytes = Integer.valueOf((String) node.attributes().get('bytes'))
        return new XUnitPad(bytes:padBytes)
    }

    @Override
    JavaPad getJavaUnit() {
        return new JavaPad(bytes:bytes)
    }
}
