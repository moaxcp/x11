package com.github.moaxcp.x11protocol.parser

import groovy.transform.ToString
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.parser.JavaStruct.javaStruct

@ToString(includeSuperProperties = true, includePackage = false, includes = ['name'])
class XTypeStruct extends XTypeObject {

    static XTypeStruct xTypeStruct(XResult result, Node node) {
        XTypeStruct struct = new XTypeStruct(basePackage: result.basePackage, javaPackage: result.javaPackage)
        struct.name = node.attributes().get('name')
        struct.addUnits(result, node)

        return struct
    }

    @Override
    JavaType getJavaType() {
        return javaStruct(this)
    }
}
