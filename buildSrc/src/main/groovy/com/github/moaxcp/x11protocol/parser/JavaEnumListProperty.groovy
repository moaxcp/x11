package com.github.moaxcp.x11protocol.parser


import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.*

class JavaEnumListProperty extends JavaListProperty {
    
    JavaEnumListProperty(JavaType javaType, XUnitListField field) {
        super(javaType, field)
    }
    @Override
    TypeName getTypeName() {
        return memberTypeName
    }
    
    @Override
    TypeName getBaseTypeName() {
        return getEnumTypeName(x11Field.resolvedEnumType.javaPackage, x11Field.resolvedEnumType.name)
    }

    TypeName getMemberTypeName() {
        return ParameterizedTypeName.get(ClassName.get(List), baseTypeName)
    }

    TypeName getIoTypeName() {
        return x11PrimativeToStorageTypeName(x11Field.resolvedType.name)
    }

    static JavaEnumListProperty javaEnumListProperty(JavaType javaType, XUnitListField field) {
        return new JavaEnumListProperty(javaType, field)
    }

    @Override
    CodeBlock getReadCode() {
        return CodeBlock.builder()
            .addStatement('$1T $2L = new $3T<>($4L)', typeName, name, ArrayList.class, lengthExpression.getExpression(TypeName.INT))
            .beginControlFlow('for(int i = 0; i < $L; i++)', lengthExpression.expression)
            .addStatement('$L.add($T.getByCode(in.read$L()))', name, baseTypeName, fromUpperUnderscoreToUpperCamel(x11Type))
            .endControlFlow()
            .build()
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.builder()
            .beginControlFlow('for($T e : $L)', baseTypeName, name)
            .addStatement('out.write$L(e.getValue())', fromUpperUnderscoreToUpperCamel(x11Type))
            .endControlFlow()
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
