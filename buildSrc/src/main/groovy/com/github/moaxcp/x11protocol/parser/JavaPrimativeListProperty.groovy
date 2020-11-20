package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.EmptyExpression
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.fromUpperUnderscoreToUpperCamel
import static com.github.moaxcp.x11protocol.generator.Conventions.x11PrimativeToBoxedType 

class JavaPrimativeListProperty extends JavaListProperty {

    JavaPrimativeListProperty(JavaType javaType, XUnitListField field) {
        super(javaType, field)
    }

    @Override
    ClassName getBaseTypeName() {
        return x11PrimativeToBoxedType(x11Field.resolvedType.name)
    }

    @Override
    TypeName getTypeName() {
        return ParameterizedTypeName.get(ClassName.get(List.class), baseTypeName)
    }

    static JavaPrimativeListProperty javaPrimativeListProperty(JavaType javaType, XUnitListField field) {
        return new JavaPrimativeListProperty(javaType, field)
    }

    @Override
    CodeBlock getDeclareAndReadCode() {
        return declareAndInitializeTo(readCode)
    }

    @Override
    CodeBlock getReadCode() {
        if(lengthExpression instanceof EmptyExpression) {
            return CodeBlock.of('in.read$L($L)', fromUpperUnderscoreToUpperCamel(x11Type), CodeBlock.of('javaStart - length'))
        } else {
            return CodeBlock.of('in.read$L($L)', fromUpperUnderscoreToUpperCamel(x11Type), lengthExpression.getExpression(TypeName.INT))
        }
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.builder()
            .addStatement("out.write${fromUpperUnderscoreToUpperCamel(x11Type)}($name)")
            .build()
    }

    @Override
    CodeBlock getSizeExpression() {
        switch(x11Type) {
            case 'BOOL':
            case 'byte':
            case 'BYTE':
            case 'INT8':
            case 'CARD8':
            case 'char':
            case 'void':
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
        throw new UnsupportedOperationException("type not supported $x11Type")
    }

    @Override
    Optional<Integer> getFixedSize() {
        return Optional.empty()
    }
}
