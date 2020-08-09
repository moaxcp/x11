package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.Expression
import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.convertX11VariableNameToJava
import static com.github.moaxcp.x11protocol.generator.Conventions.fromUpperToUpperCamel
import static com.github.moaxcp.x11protocol.generator.Conventions.getX11Primatives
import static com.github.moaxcp.x11protocol.generator.Conventions.x11PrimativeToJavaTypeName

class JavaPrimativeListProperty extends JavaListProperty {
    String name
    String x11Primative
    TypeName baseType
    TypeName typeName
    Expression lengthExpression
    boolean readOnly
    boolean localOnly

    static JavaListProperty javaPrimativeListProperty(XUnitListField field) {
        XTypeResolved resolvedType = field.resolvedType
        if(!x11Primatives.contains(resolvedType.name)) {
            throw new IllegalArgumentException("Could not find ${resolvedType.name} in primative types $x11Primatives")
        }

        String x11Primative = resolvedType.name
        TypeName baseType = x11PrimativeToJavaTypeName(resolvedType.name)
        TypeName typeName = ArrayTypeName.of(baseType)

        return new JavaPrimativeListProperty(
            name:convertX11VariableNameToJava(field.name),
            x11Primative:x11Primative,
            baseTypeName: baseType,
            typeName: typeName,
            lengthExpression: field.lengthExpression,
            readOnly: field.readOnly
        )
    }

    @Override
    CodeBlock getReadCode() {
        return declareAndInitializeTo("in.read${fromUpperToUpperCamel(x11Primative)}(${lengthExpression.expression})")
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.of("out.write${fromUpperToUpperCamel(x11Primative)}($name)")
    }
}
