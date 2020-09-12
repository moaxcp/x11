package com.github.moaxcp.x11protocol.parser


import com.github.moaxcp.x11protocol.parser.expression.ExpressionFactory
import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.*

class JavaPrimativeListProperty extends JavaListProperty {
    String x11Primative

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
        return CodeBlock.builder()
            .addStatement("out.write${fromUpperUnderscoreToUpperCamel(x11Primative)}($name)")
            .build()
    }

    @Override
    CodeBlock getSize() {
        switch(x11Primative) {
            case 'BOOL':
            case 'byte':
            case 'BYTE':
            case 'INT8':
            case 'CARD8':
            case 'char':
                return CodeBlock.of('1 * $L.length', name)
            case 'INT16':
            case 'CARD16':
                return CodeBlock.of('2 * $L.length', name)
            case 'INT32':
            case 'CARD32':
            case 'float':
            case 'fd':
                return CodeBlock.of('4 * $L.length', name)
            case 'CARD64':
            case 'double':
                return CodeBlock.of('8 * $L.length', name)
        }
        throw new UnsupportedOperationException("type not supported $x11Primative")
    }
}
