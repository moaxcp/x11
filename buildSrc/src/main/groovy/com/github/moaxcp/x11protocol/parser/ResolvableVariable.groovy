package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.generator.Conventions
import com.squareup.javapoet.TypeName

/**
 * An XUnit that can be assigned to a java variable. This may be a local variable or a member variable of a class.
 * <p>
 *     When reading from an input the variable is assigned. When writing to an output stream the variable is written
 *     to the stream.
 */
abstract class ResolvableVariable implements JavaVariableXUnit {
    XResult result
    String type
    String name

    String getJavaName() {
        return Conventions.convertX11VariableNameToJava(name)
    }

    TypeName getJavaTypeName() {
        return resolvedType.getJavaType()
    }

    XType getResolvedType() {
        return result.resolveXType(type)
    }
}
