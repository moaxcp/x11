package com.github.moaxcp.x11protocol.parser

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.parser.XType.xidType

@EqualsAndHashCode
@ToString
class XResult {
    String header
    String extensionXName
    String extensionName
    int majorVersion
    int minorVersion
    Map<String, XResult> imports = [:]
    Map<String, XType> xidTypes = [:]
    Map<String, XType> xidUnion = [:]
    Map<String, XStruct> structs = [:]

    void addXidtype(Node node) {
        XType type = xidType(this, node)
        xidTypes.put(type.name, type)
    }

    void addXidunion(Node node) {
        XType type = xidType(this, node)
        xidTypes.put(type.name, type)
    }

    void addImport(String name, XResult result) {
        imports.put(name, result)
    }

    void addStruct(Node node) {
        String name = node.attributes().get('name')
        structs.put(name, XStruct.getXStruct(node))
    }

    XType resolveXType(String type) {

    }
}
