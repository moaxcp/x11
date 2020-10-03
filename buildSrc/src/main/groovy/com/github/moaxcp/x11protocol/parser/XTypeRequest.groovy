package com.github.moaxcp.x11protocol.parser

import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.parser.JavaRequest.javaRequest

class XTypeRequest extends XTypeObject {
    int opCode
    XTypeReply reply
    
    XTypeRequest(Map map) {
        super(map)
        opCode = map.opCode ?: 0
    }
    
    static XTypeRequest xTypeRequest(XResult result, Node node) {
        XTypeRequest request = new XTypeRequest(result: result, name: node.attributes().get('name'), opCode: Integer.valueOf((String) node.attributes().get('opcode')), basePackage: result.basePackage, javaPackage: result.javaPackage)
        request.addUnits(result, node)

        Node replyNode = node.childNodes().find { it.name() == 'reply' }

        if(replyNode) {
            XTypeReply reply = new XTypeReply(result: result, name: node.attributes().get('name'), basePackage: result.basePackage, javaPackage: result.javaPackage)
            reply.addUnits(result, replyNode)
            request.reply = reply
        }
        return request
    }

    @Override
    JavaType getJavaType() {
        return javaRequest(this)
    }

    @Override
    void addUnits(XResult result, Node node) {
        node.childNodes().eachWithIndex { Node it, int i ->
            XUnit unit = parseXUnit(result, it)
            if(unit) {
                protocol.add(unit)
                if(i == 0) {
                    XUnit length = new XUnitField(result: result, name: 'length', type: 'CARD16', localOnly: true)
                    protocol.add(length)
                }
            }
        }
    }
}
