package com.github.moaxcp.x11protocol.parser

import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.parser.JavaReply.javaReply

class XTypeReply extends XTypeObject {

    XTypeReply(Map map) {
        super(map)
    }

    @Override
    JavaType getJavaType() {
        return javaReply(this)
    }

    @Override
    void addUnits(XResult result, Node node) {
        node.childNodes().eachWithIndex { Node it, int i ->
            XUnit unit = parseXUnit(result, it)
            if(unit) {
                protocol.add(unit)
                if(i == 0) {
                    XUnit sequence = new XUnitField(result: result, name: 'sequenceNumber', type: 'CARD16')
                    protocol.add(sequence)
                    XUnit length = new XUnitField(result: result, name: 'length', type: 'CARD32', localOnly: true)
                    protocol.add(length)
                }
            }
        }
    }
}
