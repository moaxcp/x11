package com.github.moaxcp.x11protocol.parser


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

    Tuple2<String, String> resolveType(String type) {
        Tuple2<String, String> resolved = resolveTypeRecursive(type)

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

        switch(type) {
            case 'BYTE':
                return new Tuple2<>('primative', 'BYTE')
            case 'INT8':
                return new Tuple2<>('primative', 'INT8')
            case 'CARD32':
                return new Tuple2<>('primative', 'CARD32')
            case 'float':
                return new Tuple2<>('primative', 'float')
            case 'double':
                return new Tuple2<>('primative', 'double')
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
    }
}
