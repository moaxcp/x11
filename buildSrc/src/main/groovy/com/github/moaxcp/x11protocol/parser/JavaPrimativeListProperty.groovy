package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.Expression
import com.github.moaxcp.x11protocol.parser.expression.ExpressionFactory
import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.*

class JavaPrimativeListProperty extends JavaListProperty {
    String name
    String x11Primative
    TypeName typeName
    Expression lengthExpression
    boolean readOnly
    boolean localOnly

    static JavaPrimativeListProperty javaPrimativeListProperty(JavaType javaType, XUnitListField field) {
        XType resolvedType = field.resolvedType
        if(!x11Primatives.contains(resolvedType.name)) {
            throw new IllegalArgumentException("Could not find ${resolvedType.name} in primative types $x11Primatives")
        }

        String x11Primative = resolvedType.name
        TypeName baseType = x11PrimativeToJavaTypeName(resolvedType.name)
        TypeName typeName = ArrayTypeName.of(baseType)

        JavaPrimativeListProperty property = new JavaPrimativeListProperty(
            name:convertX11VariableNameToJava(field.name),
            x11Primative:x11Primative,
            baseTypeName: baseType,
            typeName: typeName,
            readOnly: field.readOnly
        )

        property.lengthExpression = ExpressionFactory.getExpression(javaType, field.lengthExpression)
        return property
    }

    @Override
    CodeBlock getReadCode() {
        return declareAndInitializeTo("in.read${fromUpperUnderscoreToUpperCamel(x11Primative)}(${lengthExpression.expression})")
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.of("out.write${fromUpperUnderscoreToUpperCamel(x11Primative)}($name)")
    }

    @Override
    CodeBlock getSize() {
        if(baseTypeName == TypeName.BOOLEAN) {
            return CodeBlock.of('1 * $L.length', name)
        }
        if(baseTypeName == TypeName.BYTE) {
            return CodeBlock.of('1 * $L.length', name)
        }
        if(baseTypeName == TypeName.SHORT) {
            return CodeBlock.of('2 * $L.length', name)
        }
        if(baseTypeName == TypeName.CHAR) {
            return CodeBlock.of('2 * $L.length', name)
        }
        if(baseTypeName == TypeName.INT) {
            return CodeBlock.of('4 * $L.length', name)
        }
        if(baseTypeName == TypeName.LONG) {
            return CodeBlock.of('8 * $L.length', name)
        }
        throw new UnsupportedOperationException("type not supported $memberTypeName")
    }
}
