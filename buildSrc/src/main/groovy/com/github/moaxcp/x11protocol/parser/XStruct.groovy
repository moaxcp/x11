package com.github.moaxcp.x11protocol.parser
import groovy.util.slurpersupport.Node

class XStruct extends XType {
    List<ResolvableXUnit> protocol

    static XStruct getXStruct(XResult result, Node node) {
        XStruct struct = new XStruct()
        struct.result = result
        struct.name = node.attributes().get('name')
        struct.type = node.name()

        return struct
    }
}
