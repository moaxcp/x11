package com.github.moaxcp.x11protocol.parser
import groovy.util.slurpersupport.Node

class XStruct {
    String name
    List<XUnit> protocol

    static XStruct getXStruct(Node node) {
        XStruct struct = new XStruct()
        struct.name = node.attributes().get('name')

        return struct
    }
}
