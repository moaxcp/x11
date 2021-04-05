package com.github.moaxcp.x11protocol.xcbparser


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
        return getEnumClassName(x11Field.resolvedEnumType.javaPackage, x11Field.resolvedEnumType.name)
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
    CodeBlock getDeclareAndReadCode() {
        return CodeBlock.builder()
            .addStatement('$1T $2L = new $3T<>($4L)', typeName, name, ArrayList.class, lengthExpression.getExpression(TypeName.INT))
            .beginControlFlow('for(int i = 0; i < $L; i++)', lengthExpression.expression)
            .addStatement('$L.add($T.getByCode(in.read$L()))', name, baseTypeName, fromUpperUnderscoreToUpperCamel(x11Type))
            .endControlFlow()
            .build()
    }

    @Override
    CodeBlock getReadCode() {
        throw new IllegalStateException()
    }

    @Override
    void addWriteCode(CodeBlock.Builder code) {
        code.beginControlFlow('for($T e : $L)', baseTypeName, name)
            .addStatement('out.write$L(e.getValue())', fromUpperUnderscoreToUpperCamel(x11Type))
            .endControlFlow()
    }

    @Override
    CodeBlock getSizeExpression() {
        CodeBlock actualSize
        switch(x11Type) {
            case 'BOOL':
            case 'byte':
            case 'BYTE':
            case 'INT8':
            case 'CARD8':
            case 'char':
            case 'void':
                actualSize = CodeBlock.of('1 * $L.size()', name)
                break
            case 'INT16':
            case 'CARD16':
                actualSize = CodeBlock.of('2 * $L.size()', name)
                break
            case 'INT32':
            case 'CARD32':
            case 'float':
            case 'fd':
                actualSize = CodeBlock.of('4 * $L.size()', name)
                break
            case 'CARD64':
            case 'double':
                actualSize = CodeBlock.of('8 * $L.size()', name)
                break
            default:
                throw new UnsupportedOperationException("type not supported $x11Type")
        }

        if(bitcaseInfo) {
            return CodeBlock.of('(is$LEnabled($T.$L) ? $L : 0)', bitcaseInfo.maskField.capitalize(), bitcaseInfo.enumType, bitcaseInfo.enumItem, actualSize)
        }
        return actualSize
    }

    @Override
    Optional<Integer> getFixedSize() {
        return Optional.empty()
    }
}
