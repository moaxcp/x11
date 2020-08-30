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
        return CodeBlock.builder()
            .addStatement("\$1T \$2L = \$1T.getByCode(in.read${fromUpperUnderscoreToUpperCamel(x11Primative)}())", memberTypeName, name)
            .build()
    }

    @Override
    CodeBlock getWriteCode() {
        if(ioTypeName != TypeName.INT) {
            return CodeBlock.builder()
                .addStatement("out.write${fromUpperUnderscoreToUpperCamel(x11Primative)}((\$T) \$L.getValue())", ioTypeName, name)
                .build()
        } else {
            return CodeBlock.builder()
                .addStatement("out.write${fromUpperUnderscoreToUpperCamel(x11Primative)}(\$L.getValue())", name)
                .build()
        }
    }

    @Override
    CodeBlock getSize() {
        if(ioTypeName == TypeName.BYTE) {
            return CodeBlock.of('1')
        }
        if(ioTypeName == TypeName.SHORT) {
            return CodeBlock.of('2')
        }
        if(ioTypeName == TypeName.CHAR) {
            return CodeBlock.of('2')
        }
        if(ioTypeName == TypeName.INT) {
            return CodeBlock.of('4')
        }
        if(ioTypeName == TypeName.LONG) {
            return CodeBlock.of('8')
        }
        throw new UnsupportedOperationException("type not supported $memberTypeName")
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
