package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.generator.Conventions
import groovy.transform.EqualsAndHashCode
import groovy.transform.Memoized
import groovy.transform.ToString
import groovy.util.slurpersupport.Node

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
    Set<String> xidTypes = []
    Set<String> xidUnions = []
    Map<String, String> typedefs = [:]
    Map<String, XTypeStruct> structs = [:]
    Map<String, XTypeEnum> enums = [:]
    Map<String, XTypeUnion> unions = [:]
    Map<String, XTypeEvent> events = [:]
    Map<String, XTypeError> errors = [:]

    static Map<String, XTypePrimative> primatives = [:]

    static {
        primatives = Conventions.x11Primatives.collectEntries {
            [(it):new XTypePrimative(name:it)]
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
            case 'event':
                addEvent(node)
                break
            case 'eventcopy':
                addEventCopy(node)
                break
            case 'eventstruct':
                addEventStruct(node)
                break
            case 'union':
                addUnion(node)
                break
            case 'error':
                addError(node)
                break
            case 'errorcopy':
                addErrorCopy(node)
                break
            case 'request':
                addRequest(node)
                break
            default:
                throw new IllegalArgumentException("could not parse ${node.name()}")
        }
    }

    void addXidtype(Node node) {
        xidTypes.add((String) node.attributes().get('name'))
    }

    void addXidunion(Node node) {
        xidUnions.add((String) node.attributes().get('name'))
    }

    void addTypeDef(Node node) {
        typedefs.put((String) node.attributes().get('newname'), (String) node.attributes().get('oldname'))
    }

    void addImport(XResult result) {
        imports.put(result.header, result)
    }

    void addEnum(Node node) {
        String name = node.attributes().get('name')
        enums.put(name, XTypeEnum.xTypeEnum(this, node))
    }

    void addStruct(Node node) {
        String name = node.attributes().get('name')
        structs.put(name, XTypeStruct.xTypeStruct(this, node))
    }

    void addEvent(Node node) {
        String name = node.attributes().get('name')
        events.put(name, XTypeEvent.xTypeEvent(this, node))
    }

    void addEventCopy(Node node) {
        String name = node.attributes().get('name')
        events.put(name, XTypeEvent.xTypeEventCopy(this, node))
    }

    void addEventStruct(Node node) {

    }

    void addUnion(Node node) {
        String name = node.attributes().get('name')
        unions.put(name, XTypeUnion.xTypeUnion(this, node))
    }

    void addError(Node node) {
        String name = node.attributes().get('name')
        errors.put(name, XTypeError.xTypeError(this, node))
    }

    void addErrorCopy(Node node) {
        String name = node.attributes().get('name')
        errors.put(name, XTypeError.xTypeErrorCopy(this, node))
    }

    void addRequest(Node node) {

    }

    @Memoized
    <T extends XType> T resolveXType(String type) {
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
            return (T) resolved
        }

        resolved = resolveTypeRecursive(type)

        if(!resolved) {
            throw new IllegalArgumentException("could not resolve $type")
        }

        return resolved
    }

    XType resolveTypeRecursive(String type) {
        XType fromLocal = resolveLocal(type)

        if(fromLocal) {
            return fromLocal
        }

        XType fromImport = imports.values().collect {
            it.resolveTypeRecursive(type)
        }.find {
            it
        }

        if(fromImport) {
            return fromImport
        }

        return null
    }

    XType resolveLocal(String type) {
        String typeDef = resolveTypeDef(type)
        if(typeDef) {
            type = typeDef
        }
        if(xidTypes.contains(type) || xidUnions.contains(type)) {
            type = 'CARD32'
        }
        XType xType = primatives[type] ?: structs[type] ?: unions[type] ?: enums[type] ?: events[type] ?: errors[type]

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
