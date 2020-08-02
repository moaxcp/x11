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
    Map<String, XType> xidTypes = [:]
    Map<String, XType> xidUnions = [:]
    Map<String, XStruct> structs = [:]

    void addXidtype(Node node) {
        XType type = xidType(this, node)
        xidTypes.put(type.name, type)
    }

    void addXidunion(Node node) {
        XType type = xidUnionType(this, node)
        xidUnions.put(type.name, type)
    }

    void addImport(String name, XResult result) {
        imports.put(name, result)
    }

    void addStruct(Node node) {
        String name = node.attributes().get('name')
        structs.put(name, XStruct.getXStruct(node))
    }

    XType resolveXType(String type) {
        if(type.contains(':')) {
            String specificImport = type.substring(0, type.indexOf(':'))
            String actualType = type.substring(type.indexOf(':') + 1)
            if(header == specificImport) {
                return resolveLocal(actualType)
            } else {
                return imports.get(specificImport).resolveTypeRecursive(actualType)
            }
        }
        XType resolved = resolveTypeRecursive(type)

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
        XType xType = xidTypes[type] ?: xidUnions[type] ?:
            structs[type]

        if(xType) {
            return xType
        }

        return null
    }
}
