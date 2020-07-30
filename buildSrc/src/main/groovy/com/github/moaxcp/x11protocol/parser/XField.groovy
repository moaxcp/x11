package com.github.moaxcp.x11protocol.parser

class XField {
    XResult xResult
    String type
    String name
    String mask
    String enumType

    XType getResolvedType() {
        xResult.resolveXType(type)
    }
}
