package com.github.moaxcp.x11protocol.xcbparser

import groovy.transform.ToString
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.xcbparser.JavaStruct.javaStruct

@ToString(includeSuperProperties = true, includePackage = false, includes = ['name'])
class XTypeStruct extends XTypeObject {

    XTypeStruct(Map map) {
        super(map)
    }

    static XTypeStruct xTypeStruct(XResult result, Node node) {
        XTypeStruct struct = new XTypeStruct(result:result, name: node.attributes().get('name'), basePackage: result.basePackage, javaPackage: result.javaPackage)
        struct.addUnits(result, node)

        return struct
    }

    @Override
    JavaType getJavaType() {
        return javaStruct(this)
    }
}
