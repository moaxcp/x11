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
    Map<String, Node> requests = [:]

    @Memoized
    Tuple2<String, String> resolveType(String type) {
        Tuple2<String, String> resolved
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

    private Tuple2<String, String> resolveTypeRecursive(String type) {
        Tuple2<String, String> fromImport = imports.values().collect {
            it.resolveTypeRecursive(type)
        }.find {
            it
        }

        if(fromImport) {
            return fromImport
        }

        return resolveLocal(type)
    }

    private Tuple2<String, String> resolveLocal(String type) {
        type = resolveTypeDef(type) ?: type

        Tuple2<String, String> fromObjects = [structs, unions, enums, errors, errorCopies, events, eventCopies, requests].collect {
            it.containsKey(type) ? new Tuple2<>(header, type) : null
        }.find { it }
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
                return new Tuple2<>('primative', 'BOOL')
            case 'BYTE':
                return new Tuple2<>('primative', 'BYTE')
            case 'INT8':
                return new Tuple2<>('primative', 'INT8')
            case 'INT16':
                return new Tuple2<>('primative', 'INT16')
            case 'INT32':
                return new Tuple2<>('primative', 'INT32')
            case 'CARD8':
                return new Tuple2<>('primative', 'CARD8')
            case 'CARD16':
                return new Tuple2<>('primative', 'CARD16')
            case 'CARD32':
                return new Tuple2<>('primative', 'CARD32')
            case 'CARD64':
                return new Tuple2<>('primative', 'CARD64')
            case 'float':
                return new Tuple2<>('primative', 'float')
            case 'double':
                return new Tuple2<>('primative', 'double')
            case 'char':
                return new Tuple2<>('primative', 'char')
            case 'void':
                return new Tuple2<>('primative', 'void')
            default:
                return null
        }
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
