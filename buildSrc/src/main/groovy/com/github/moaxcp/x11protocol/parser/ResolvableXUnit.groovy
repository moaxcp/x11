package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.generator.Conventions

abstract class ResolvableXUnit implements XUnit {
    XResult result
    String type
    String name

    String getJavaName() {
        return Conventions.convertX11VariableNameToJava(name)
    }

    XType getResolvedType() {
        return result.resolveXType(type)
    }
}
