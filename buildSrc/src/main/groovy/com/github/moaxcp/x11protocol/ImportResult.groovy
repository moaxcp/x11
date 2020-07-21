package com.github.moaxcp.x11protocol

class ImportResult {
    List<File> filesImported = []
    /**
     * map of package name to x11 -> javaType entries
     */
    Map<String, Map<String, String>> x11ToJavaTypes = [:]

    /**
     * defined types (typedef, xid, xidunion)
     */
    Map<String, String> definedTypes = [:]

    void addResult(ImportResult result) {
        definedTypes.putAll(result.definedTypes)

        result.x11ToJavaTypes.each {
            x11ToJavaTypes.merge(it.key, it.value) {first, second ->
                first + second
            }
        }
    }
}
