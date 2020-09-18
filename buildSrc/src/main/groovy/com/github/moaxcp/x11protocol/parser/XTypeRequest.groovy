package com.github.moaxcp.x11protocol.parser

import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.parser.JavaRequest.javaRequest

class XTypeRequest extends XTypeObject {
    int opCode
    
    XTypeRequest(Map map) {
        super(map)
        opCode = map.opCode ?: 0
    }
    
    static XTypeRequest xTypeRequest(XResult result, Node node) {
        XTypeRequest request = new XTypeRequest(result: result, name: node.attributes().get('name'), opCode: Integer.valueOf((String) node.attributes().get('opcode')), basePackage: result.basePackage, javaPackage: result.javaPackage)
        request.addUnits(result, node)
        return request
    }

    @Override
    JavaType getJavaType() {
        return javaRequest(this)
    }
}
