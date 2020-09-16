package com.github.moaxcp.x11protocol.parser

import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.parser.JavaError.javaError

class XTypeError extends XTypeObject {
    int number

    XTypeError(Map map) {
        super(map)
        number = map.number ?: 0
    }

    static XTypeError xTypeError(XResult result, Node node) {
        XTypeError error = new XTypeError(result: result, name: node.attributes().get('name'), basePackage: result.basePackage, javaPackage: result.javaPackage)
        error.number = Integer.valueOf((String) node.attributes().get('number'))
        error.addUnits(result, node)

        return error
    }
    
    static XTypeError xTypeErrorCopy(XResult result, Node node) {
        XTypeError error = new XTypeError(result: result, name: node.attributes().get('name'), basePackage: result.basePackage, javaPackage: result.javaPackage)
        error.number = Integer.valueOf((String) node.attributes().get('number'))
        XTypeError ref = result.resolveXType((String) node.attributes().get('ref'))
        error.superTypes = ref.superTypes
        error.protocol = ref.protocol
        
        return error
    }

    @Override
    JavaType getJavaType() {
        return javaError(this)
    }
}
