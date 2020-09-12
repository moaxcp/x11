package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.ExpressionFactory
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.*

class JavaEnumListProperty extends JavaListProperty {
    String x11Primative
    TypeName ioTypeName

    static JavaEnumListProperty javaEnumListProperty(JavaType javaType, XUnitListField field) {
        XType resolvedType = field.resolvedType
        TypeName baseTypeName = getEnumTypeName(field.result.javaPackage, field.resolvedEnumType.name)
        TypeName typeName = ParameterizedTypeName.get(ClassName.get(List), baseTypeName)
        return new JavaEnumListProperty(
            name:convertX11VariableNameToJava(field.name),
            x11Primative:resolvedType.name,
            baseTypeName: baseTypeName,
            typeName: typeName,
            ioTypeName:x11PrimativeToJavaTypeName(resolvedType.name),
            lengthExpression: ExpressionFactory.getExpression(javaType, field.lengthExpression)
        )
    }

    @Override
    CodeBlock getReadCode() {
        return CodeBlock.builder()
            .addStatement('$1T $2L = new $3T<>($4L)', typeName, name, ArrayList.class, lengthExpression.expression)
            .beginControlFlow('for(int i = 0; i < $L; i++)', lengthExpression.expression)
            .addStatement('$L.add($T.getByCode(in.read$L()))', name, baseTypeName, fromUpperUnderscoreToUpperCamel(x11Primative))
            .endControlFlow()
            .build()
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.builder()
            .beginControlFlow('for($T e : $L)', baseTypeName, name)
            .addStatement('out.write$L(e.getValue())', fromUpperUnderscoreToUpperCamel(x11Primative))
            .endControlFlow()
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
                return CodeBlock.of('1 * $L.size()', name)
            case 'INT16':
            case 'CARD16':
                return CodeBlock.of('2 * $L.size()', name)
            case 'INT32':
            case 'CARD32':
            case 'float':
            case 'fd':
                return CodeBlock.of('4 * $L.size()', name)
            case 'CARD64':
            case 'double':
                return CodeBlock.of('8 * $L.size()', name)
        }
        throw new UnsupportedOperationException("type not supported $x11Primative")
    }
}
