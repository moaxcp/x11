package com.github.moaxcp.x11protocol.generator

import com.squareup.javapoet.TypeSpec

class JavaResult {
    String packageName
    Map<String, TypeSpec.Builder> structs = [:]
    Map<String, TypeSpec.Builder> unions = [:]
    Map<String, TypeSpec.Builder> enums = [:]
    Map<String, TypeSpec.Builder> errors = [:]
    Map<String, TypeSpec.Builder> events = [:]
    Map<String, TypeSpec.Builder> eventStructs = [:]
    Map<String, TypeSpec.Builder> requests = [:]
    Map<String, TypeSpec.Builder> replies = [:]
}
