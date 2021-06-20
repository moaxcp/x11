package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.ClassName

import static java.util.Objects.requireNonNull

abstract class XType implements XTypeUnit {
    final XResult result
    final String basePackage
    final String javaPackage
    final String name

    XType(Map map) {
        result = requireNonNull(map.result, 'result must not be null')
        basePackage = map.basePackage
        javaPackage = map.javaPackage
        name = requireNonNull(map.name, 'name must not be null')
    }

    abstract Optional<ClassName> getCaseSuperName()
    abstract List<String> getCaseNames()
}
