package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.*

class JavaEnumListProperty extends JavaListProperty {
    String x11Primative
    TypeName ioTypeName

    static JavaListProperty javaEnumListProperty(XUnitListField field) {
        XTypeResolved resolvedType = field.resolvedType
        return new JavaEnumListProperty(
            name:convertX11VariableNameToJava(field.name),
            x11Primative:field.resolvedEnumType.name,
            baseTypeName: getEnumTypeName(field.result.javaPackage, field.resolvedEnumType.name),
            ioTypeName:x11PrimativeToJavaTypeName(resolvedType.name)
        )
    }

    @Override
    CodeBlock getReadCode() {
        return null
    }

    @Override
    CodeBlock getWriteCode() {
        return null
    }
}
