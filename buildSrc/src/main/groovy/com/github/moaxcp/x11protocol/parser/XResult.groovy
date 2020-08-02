package com.github.moaxcp.x11protocol.parser

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.parser.XType.*

@EqualsAndHashCode
@ToString
class XResult {
    String header
    String extensionXName
    String extensionName
    int majorVersion
    int minorVersion
    Map<String, XResult> imports = [:]
    Map<String, XType> primatives = [:]
    Map<String, XType> xidTypes = [:]
    Map<String, XType> xidUnions = [:]
    Map<String, String> typedefs = [:]
    Map<String, XStruct> structs = [:]

    XResult() {
        primatives['BOOL'] = new XType(result:this, type:'primative', name:'BOOL')
        primatives['BYTE'] = new XType(result:this, type:'primative', name:'BYTE')
        primatives['INT8'] = new XType(result:this, type:'primative', name:'INT8')
        primatives['INT16'] = new XType(result:this, type:'primative', name:'INT16')
        primatives['INT32'] = new XType(result:this, type:'primative', name:'INT32')
        primatives['CARD8'] = new XType(result:this, type:'primative', name:'CARD8')
        primatives['CARD16'] = new XType(result:this, type:'primative', name:'CARD16')
        primatives['CARD32'] = new XType(result:this, type:'primative', name:'CARD32')
        primatives['CARD64'] = new XType(result:this, type:'primative', name:'CARD64')
        primatives['fd'] = new XType(result:this, type:'primative', name:'fd')
        primatives['float'] = new XType(result:this, type:'primative', name:'float')
        primatives['double'] = new XType(result:this, type:'primative', name:'double')
        primatives['char'] = new XType(result:this, type:'primative', name:'char')
        primatives['void'] = new XType(result:this, type:'primative', name:'void')
    }

    void addXidtype(Node node) {
        XType type = xidType(this, node)
        xidTypes.put(type.name, type)
    }

    void addXidunion(Node node) {
        XType type = xidUnionType(this, node)
        xidUnions.put(type.name, type)
    }

    void addTypeDef(Node node) {
        typedefs.put((String) node.attributes().get('newname'), (String) node.attributes().get('oldname'))
    }

    void addImport(XResult result) {
        imports.put(result.header, result)
    }

    void addStruct(Node node) {
        String name = node.attributes().get('name')
        structs.put(name, XStruct.getXStruct(this, node))
    }

    XType resolveXType(String type) {
        XType resolved
        if(type.contains(':')) {
            String specificImport = type.substring(0, type.indexOf(':'))
            String actualType = type.substring(type.indexOf(':') + 1)
            if(header == specificImport) {
                resolved = resolveLocal(actualType)
            } else {
                resolved = imports.get(specificImport).resolveTypeRecursive(actualType)
            }
        }
        if(resolved) {
            return resolved
        }

        resolved = resolveTypeRecursive(type)

        if(!resolved) {
            throw new IllegalArgumentException("could not resolve $type")
        }

        return resolved
    }

    XType resolveTypeRecursive(String type) {
        XType fromImport = imports.values().collect {
            it.resolveTypeRecursive(type)
        }.find {
            it
        }

        if(fromImport) {
            return fromImport
        }

        return resolveLocal(type)
    }

    XType resolveLocal(String type) {
        String typeDef = resolveTypeDef(type)
        if(typeDef) {
            type = typeDef
        }
        XType xType = primatives[type] ?: xidTypes[type] ?: xidUnions[type] ?: structs[type]

        if(xType) {
            return xType
        }

        return null
    }

    String resolveTypeDef(String type) {
        String resultType = typedefs.find { it.key == type }?.value
        if(!resultType) {
            return null
        }
        String nextType = resolveTypeDef(resultType)
        if(!nextType) {
            return resultType
        }
        return nextType
    }
}
