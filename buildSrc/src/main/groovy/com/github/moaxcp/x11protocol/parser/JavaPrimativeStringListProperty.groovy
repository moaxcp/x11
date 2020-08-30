package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.ExpressionFactory
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.convertX11VariableNameToJava
import static com.github.moaxcp.x11protocol.generator.Conventions.x11PrimativeToJavaTypeName

class JavaPrimativeStringListProperty extends JavaListProperty {
    String x11Primative

    static JavaPrimativeStringListProperty javaPrimativeStringListProperty(JavaType javaType, XUnitListField field) {
        XType resolvedType = field.resolvedType
        if(resolvedType.name != 'char') {
            throw new IllegalArgumentException("Only char is supported. Got ${resolvedType.name}")
        }

        String x11Primative = resolvedType.name
        TypeName baseType = x11PrimativeToJavaTypeName(resolvedType.name)
        TypeName typeName = ClassName.get(String.class)

        JavaPrimativeStringListProperty property = new JavaPrimativeStringListProperty(
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
        return declareAndInitializeTo("in.readString8(${lengthExpression.expression})")
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.builder()
            .addStatement("out.writeString8($name)")
            .build()
    }

    @Override
    CodeBlock getSize() {
        return CodeBlock.of('$L.length()', name)
    }
}
