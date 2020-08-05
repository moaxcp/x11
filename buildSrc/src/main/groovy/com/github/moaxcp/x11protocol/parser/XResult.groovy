package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.generator.Conventions
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.parser.XType.xidType
import static com.github.moaxcp.x11protocol.parser.XType.xidUnionType

@ToString(includePackage = false, includes='header')
@EqualsAndHashCode
class XResult {
    File file
    String basePackage
    String header
    String extensionXName
    String extensionName
    Boolean extensionMultiword
    int majorVersion
    int minorVersion
    Map<String, XResult> imports = [:]
    Map<String, XType> primatives = [:]
    Map<String, XType> xidTypes = [:]
    Map<String, XType> xidUnions = [:]
    Map<String, String> typedefs = [:]
    Map<String, XStruct> structs = [:]
    Map<String, XEnum> enums = [:]

    XResult() {
        primatives = Conventions.x11Primatives.collectEntries {
            [(it):new XType(result:this, type:'primative', name:it)]
        }
    }

    String getJavaPackage() {
        return "$basePackage.$header"
    }

    void addNode(Node node) {
        switch(node.name()) {
            case 'xidtype':
                addXidtype(node)
                break
            case 'xidunion':
                addXidunion(node)
                break
            case 'typedef':
                addTypeDef(node)
                break
            case 'enum':
                addEnum(node)
                break
            case 'struct':
                addStruct(node)
                break
            default:
                throw new IllegalArgumentException("could not parse $node")
        }
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

    void addEnum(Node node) {
        String name = node.attributes().get('name')
        enums.put(name, XEnum.getXEnum(this, node))
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
        XType xType = primatives[type] ?: xidTypes[type] ?: xidUnions[type] ?: structs[type] ?: enums[type]

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
