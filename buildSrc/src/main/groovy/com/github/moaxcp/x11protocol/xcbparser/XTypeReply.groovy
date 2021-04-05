package com.github.moaxcp.x11protocol.xcbparser

import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.xcbparser.JavaReply.javaReply

class XTypeReply extends XTypeObject {

    XTypeReply(Map map) {
        super(map)
    }

    static XTypeReply xTypeReply(String name, XResult result, Node node) {
        XTypeReply reply = new XTypeReply(result: result, name: name, basePackage: result.basePackage, javaPackage: result.javaPackage)
        reply.addUnits(result, node)
        reply.protocol.add(0, new XUnitField(result: result, name: 'RESPONSECODE', type:'CARD8', constantValue: 1))
        if(reply.protocol.size() == 1) {
            reply.protocol.add(new XUnitPad(bytes: 1))
        }
        if(reply.protocol.size() == 2) {
            reply.protocol.add(new XUnitField(result: result, name: 'sequenceNumber', type: 'CARD16'))
            reply.protocol.add(new XUnitField(result: result, name: 'length', type: 'CARD32', localOnly: true))
        } else {
            reply.protocol.add(2, new XUnitField(result: result, name: 'sequenceNumber', type: 'CARD16'))
            reply.protocol.add(3, new XUnitField(result: result, name: 'length', type: 'CARD32', localOnly: true))

        }
        return reply
    }

    @Override
    JavaType getJavaType() {
        return javaReply(this)
    }
}
