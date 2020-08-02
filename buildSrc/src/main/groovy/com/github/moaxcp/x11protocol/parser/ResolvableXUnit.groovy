package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.generator.Conventions
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.x11PrimativeToJavaTypeName

abstract class ResolvableXUnit implements XUnit {
    XResult result
    String type
    String name

    String getJavaName() {
        return Conventions.convertX11VariableNameToJava(name)
    }

    TypeName getJavaType() {
        XType type = resolvedType
        switch(type.type) {
            case 'primative':
                return x11PrimativeToJavaTypeName(type.name)
            case 'xid':
            case 'xidunion':
                return x11PrimativeToJavaTypeName('CARD32')
        }
        return null
    }

    XType getResolvedType() {
        return result.resolveXType(type)
    }
}
