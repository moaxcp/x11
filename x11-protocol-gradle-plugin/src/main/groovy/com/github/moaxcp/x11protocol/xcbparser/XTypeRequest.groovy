package com.github.moaxcp.x11protocol.xcbparser


import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.xcbparser.JavaRequest.javaRequest

class XTypeRequest extends XTypeObject {
    int opCode
    XTypeReply reply
    
    XTypeRequest(Map map) {
        super(map)
        opCode = map.opCode ?: 0
    }

    static XTypeRequest xTypeRequest(XResult result, Node node) {
        int opCode = Integer.valueOf((String) node.attributes().get('opcode')) ?: 0
        XTypeRequest request = new XTypeRequest(result: result, name: node.attributes().get('name'), opCode: opCode, basePackage: result.basePackage, javaPackage: result.javaPackage)
        request.addUnits(result, node)
        if (result.header == 'xproto') {
            request.protocol.add(0, new XUnitField(result: result, name: 'OPCODE', type:'CARD8', constantValue: request.opCode))
            if(request.protocol.size() == 1) {
                request.protocol.add(new XUnitPad(bytes: 1))
            } else {
                XUnit firstField = request.protocol[1]
                if(firstField instanceof XUnitPad && firstField.bytes != 1) {
                    request.protocol.add(1, new XUnitPad(bytes: 1))
                } else if(firstField instanceof XUnitField && !['BOOL', 'BYTE', 'INT8', 'CARD8', 'char', 'void'].contains(firstField.resolvedType.name)) {
                    request.protocol.add(1, new XUnitPad(bytes: 1))
                }
            }
            request.protocol.add(2, new XUnitField(result: result, name: 'length', type: 'CARD16', localOnly: true))
        } else {
            request.protocol.add(0, new XUnitField(result: result, name: 'majorOpcode', type:'CARD8', localOnly: true))
            request.protocol.add(1, new XUnitField(result: result, name: 'OPCODE', type:'CARD8', constantValue: request.opCode))
            request.protocol.add(2, new XUnitField(result: result, name: 'length', type: 'CARD16', localOnly: true))
        }

        Node replyNode = node.childNodes().find { it.name() == 'reply' }

        if(replyNode) {
            XTypeReply reply = XTypeReply.xTypeReply((String) node.attributes().get('name'), result, replyNode)
            request.reply = reply
        }
        return request
    }

    @Override
    JavaType getJavaType() {
        return javaRequest(this)
    }

    @Override
    JavaType getSubType(String subType) {
        return javaRequest(this, subType)
    }
}
