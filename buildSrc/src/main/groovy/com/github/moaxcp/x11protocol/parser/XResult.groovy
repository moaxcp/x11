package com.github.moaxcp.x11protocol.parser

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

    void addXidtype(String name) {
        xidTypes.put(name, new XType(result:this, type:'primative', name:'CARD32'))
    }

    void addXidunion(String name) {
        xidUnion.put(name, new XType(result:this, type:'primative', name:'CARD32'))
    }



    XType resolveXType(String type) {

    }
}
