package com.github.moaxcp.x11protocol

import com.squareup.javapoet.TypeSpec

class ParseResult {
    /**
     * typically the xcb header name for this result
     */
    String xcbType

    /**
     * The java package name for all types in this result
     */
    String packageName

    /**
     * Maps x11 types (xid, xidunion) to primative types(byte, int, long)
     */
    Map<String, String> definedTypes = [:]

    /**
     * Maps x11 types to java types (classes, interfaces, enums)
     */
    Map<String, TypeSpec> javaTypes = [:]

    void putAllDefinedTypes(Map<String, String> types) {
        definedTypes.putAll(types)
    }

    String getDefinedType(String type) {
        return definedTypes.get(type)
    }


}
