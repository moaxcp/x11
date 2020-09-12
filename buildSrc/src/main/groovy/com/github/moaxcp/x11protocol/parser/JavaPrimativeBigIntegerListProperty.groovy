package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.ExpressionFactory
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.*

class JavaPrimativeBigIntegerListProperty extends JavaListProperty {
    String x11Primative

    static JavaPrimativeBigIntegerListProperty javaPrimativeBigIntegerListProperty(JavaType javaType, XUnitListField field) {
        XType resolvedType = field.resolvedType
        if(!x11Primatives.contains(resolvedType.name)) {
            throw new IllegalArgumentException("Could not find ${resolvedType.name} in primative types $x11Primatives")
        }

        String x11Primative = resolvedType.name
        TypeName baseType = ClassName.get(BigInteger.class)
        TypeName typeName = ParameterizedTypeName.get(List.class, BigInteger.class)

        JavaPrimativeBigIntegerListProperty property = new JavaPrimativeBigIntegerListProperty(
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
        return CodeBlock.of('8 * $L.length', name)
    }
}
