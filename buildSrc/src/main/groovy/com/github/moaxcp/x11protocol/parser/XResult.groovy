package com.github.moaxcp.x11protocol.parser

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.util.slurpersupport.Node

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
        String name = node.attributes().get('name')
        xidTypes.put(name, new XType(result:this, type:'primative', name:'CARD32'))
    }

    void addXidunion(Node node) {
        String name = node.attributes().get('name')
        xidUnion.put(name, new XType(result:this, type:'primative', name:'CARD32'))
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
