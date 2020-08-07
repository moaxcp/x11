package com.github.moaxcp.x11protocol.parser

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
}
