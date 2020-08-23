package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.convertX11VariableNameToJava
import static com.github.moaxcp.x11protocol.generator.Conventions.fromUpperUnderscoreToUpperCamel
import static com.github.moaxcp.x11protocol.generator.Conventions.getEnumTypeName
import static com.github.moaxcp.x11protocol.generator.Conventions.getX11Primatives
import static com.github.moaxcp.x11protocol.generator.Conventions.x11PrimativeToJavaTypeName

/**
 * for converting fields that have an enum set
 */
class JavaEnumProperty extends JavaProperty {
    String name
    String x11Primative
    TypeName memberTypeName
    TypeName ioTypeName
    boolean readOnly
    boolean localOnly

    @Override
    TypeName getTypeName() {
        return memberTypeName
    }

    @Override
    CodeBlock getReadCode() {
        return CodeBlock.of("\$1T \$2L = \$1T.getByCode(in.read${fromUpperUnderscoreToUpperCamel(x11Primative)}())",
            memberTypeName, name)
    }

    @Override
    CodeBlock getWriteCode() {
        if(ioTypeName != TypeName.INT) {
            return CodeBlock.of("out.write${fromUpperUnderscoreToUpperCamel(x11Primative)}((\$T) \$L.getValue())",
                ioTypeName, name)
        } else {
            return CodeBlock.of("out.write${fromUpperUnderscoreToUpperCamel(x11Primative)}(\$L.getValue())",
                name)
        }
    }

    static JavaEnumProperty javaEnumProperty(XUnitField field) {
        XType resolvedType = field.resolvedType
        if(!x11Primatives.contains(resolvedType.name)) {
            throw new IllegalArgumentException("Could not find ${resolvedType.name} in primative types $x11Primatives")
        }
        return new JavaEnumProperty(
            name:convertX11VariableNameToJava(field.name),
            x11Primative:resolvedType.name,
            memberTypeName:getEnumTypeName(field.resolvedEnumType.javaPackage, field.resolvedEnumType.name),
            ioTypeName:x11PrimativeToJavaTypeName(resolvedType.name)
        )
    }
}
