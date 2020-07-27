package com.github.moaxcp.x11protocol.generator

import groovy.transform.Memoized
import groovy.util.slurpersupport.Node

class X11Result {
    File file
    String header
    String extensionXName
    String extensionName
    Boolean extensionMultiword
    Integer majorVersion
    Integer minorVersion
    Map<String, X11Result> imports = [:]
    List<String> xidTypes = []
    List<String> xidUnions = []
    Map<String, String> typedefs = [:]
    Map<String, Node> structs = [:]
    Map<String, Node> unions = [:]
    Map<String, Node> enums = [:]
    Map<String, Node> errors = [:]
    Map<String, Node> errorCopies = [:]
    Map<String, Node> events = [:]
    Map<String, Node> eventCopies = [:]
    Map<String, Node> eventStructs = [:]
    Map<String, Node> requests = [:]

    @Memoized
    Tuple3<String, String, String> resolveType(String type) {
        Tuple3<String, String, String> resolved
        if(type.contains(':')) {
            String specificImport = type.substring(0, type.indexOf(':'))
            String actualType = type.substring(type.indexOf(':') + 1)
            if(header == specificImport) {
                resolved = resolveLocal(actualType)
            } else {
                resolved = imports.get(specificImport).resolveTypeRecursive(actualType)
            }
        } else {
            resolved = resolveTypeRecursive(type)
        }

        if(!resolved) {
            throw new IllegalArgumentException("could not resolve $type")
        }
        return resolved
    }

    private Tuple3<String, String, String> resolveTypeRecursive(String type) {
        Tuple3<String, String, String> fromImport = imports.values().collect {
            it.resolveTypeRecursive(type)
        }.find {
            it
        }

        if(fromImport) {
            return fromImport
        }

        return resolveLocal(type)
    }

    private Tuple3<String, String, String> resolveLocal(String type) {
        type = resolveTypeDef(type) ?: type

        Tuple3<String, String, String> fromObjects = resolveFromObjects(type)
        if(fromObjects) {
            return fromObjects
        }

        if(xidTypes.contains(type)) {
            type = 'CARD32'
        }

        if(xidUnions.contains(type)) {
            type = 'CARD32'
        }

        switch (type) {
            case 'BOOL':
                return new Tuple3<>('xproto', 'primative', 'BOOL')
            case 'BYTE':
                return new Tuple3<>('xproto', 'primative', 'BYTE')
            case 'INT8':
                return new Tuple3<>('xproto', 'primative', 'INT8')
            case 'INT16':
                return new Tuple3<>('xproto', 'primative', 'INT16')
            case 'INT32':
                return new Tuple3<>('xproto', 'primative', 'INT32')
            case 'CARD8':
                return new Tuple3<>('xproto', 'primative', 'CARD8')
            case 'CARD16':
                return new Tuple3<>('xproto', 'primative', 'CARD16')
            case 'CARD32':
                return new Tuple3<>('xproto', 'primative', 'CARD32')
            case 'CARD64':
                return new Tuple3<>('xproto', 'primative', 'CARD64')
            case 'fd':
                return new Tuple3<>('xproto', 'primative', 'fd')
            case 'float':
                return new Tuple3<>('xproto', 'primative', 'float')
            case 'double':
                return new Tuple3<>('xproto', 'primative', 'double')
            case 'char':
                return new Tuple3<>('xproto', 'primative', 'char')
            case 'void':
                return new Tuple3<>('xproto', 'primative', 'void')
            default:
                return null
        }
    }

    Tuple3<String, String, String> resolveFromObjects(String type) {
        return [
            struct:structs.keySet(),
            union:unions.keySet(),
            enum:enums.keySet(),
            error:errors.keySet(),
            errorCopy:errorCopies.keySet(),
            event:events.keySet(),
            eventCopy:eventCopies.keySet(),
            eventStruct:eventStructs.keySet(),
            request:requests.keySet()
        ].inject(null) { result, entry ->
            if(result) {
                return result
            }
            resolveFromObjects(type, entry.key, entry.value)
        }
    }

    Tuple3<String, String, String> resolveFromObjects(String type, String objectName, Set<String> names) {
        String name = names.find { it == type }
        if(name) {
            return new Tuple3<>(header, objectName, type)
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

    Node findError(String x11Name) {
        Node node = imports.inject(null) { Node value, entry ->
            if(value) {
                return value
            }
            entry.value.errors.get(x11Name)
        }
        if(node) {
            return node
        }

        node = errors.get(x11Name)

        if(!node) {
            throw new IllegalArgumentException("could not find error $x11Name")
        }
        return node
    }

    Node findEvent(String x11Name) {
        Node node = imports.inject(null) { Node value, entry ->
            if(value) {
                return value
            }
            entry.value.events.get(x11Name)
        }
        if(node) {
            return node
        }

        node = events.get(x11Name)

        if(!node) {
            throw new IllegalArgumentException("could not find events $x11Name")
        }
        return node
    }
}
