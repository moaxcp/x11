package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.Expression
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.convertX11VariableNameToJava

class JavaListProperty implements JavaProperty {
    String name
    String x11Primative
    TypeName typeName
    Expression lengthExpression

    static JavaListProperty javaListProperty(XUnitListField field) {
        XTypeResolved resolvedType = field.resolvedType
        return new JavaListProperty(
            name:convertX11VariableNameToJava(field.name),
            x11Primative: resolvedType.name
        )
    }

    @Override
    FieldSpec getMember() {
        return null
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
