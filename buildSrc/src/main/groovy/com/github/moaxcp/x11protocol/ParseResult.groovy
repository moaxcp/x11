package com.github.moaxcp.x11protocol

import com.squareup.javapoet.TypeSpec

class ParseResult {
    String packageName
    Map<String, List<TypeSpec>> javaTypes = [:]
}
