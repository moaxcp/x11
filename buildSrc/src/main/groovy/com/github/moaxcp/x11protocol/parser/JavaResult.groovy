package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.TypeSpec

class JavaResult {
    String packageName
    Map<String, TypeSpec> javaTypes = [:]
}
