package com.github.moaxcp.x11protocol.xcbparser

import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.xcbparser.JavaError.javaError

class XTypeError extends XTypeObject {
    int number

    XTypeError(Map map) {
        super(map)
        number = map.number ?: 0
    }

    static XTypeError xTypeError(XResult result, Node node) {
        XTypeError error = new XTypeError(
            number: Integer.valueOf((String) node.attributes().get('number')),
            result: result,
            name: node.attributes().get('name'),
            basePackage: result.basePackage,
            javaPackage: result.javaPackage)
        error.addUnits(result, node)
        error.protocol.add(0, new XUnitField(result: result, name: 'RESPONSECODE', type:'CARD8', constantValue: 0))
        error.protocol.add(1, new XUnitField(result: result, name: 'CODE', type:'CARD8', constantValue: error.number))
        error.protocol.add(2, new XUnitField(result: result, name: 'sequence_number', type:'CARD16'))
        XUnitField minor = error.getField('minor_opcode')
        if(!minor) {
            error.protocol.add(3, new XUnitField(result: result, name: 'minor_opcode', type: 'CARD16'))
        }
        XUnitField major = error.getField('major_opcode')
        if(!major) {
            error.protocol.add(4, new XUnitField(result: result, name: 'major_opcode', type: 'CARD8'))
        }
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
