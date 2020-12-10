package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.*
/**
 * for converting fields that have an enum set
 */
class JavaEnumProperty extends JavaPrimativeProperty {

    JavaEnumProperty(JavaType javaType, XUnitField field) {
        super(javaType, field)
    }

    static JavaEnumProperty javaEnumProperty(JavaType javaType, XUnitField field) {
        return new JavaEnumProperty(javaType, field)
    }

    @Override
    CodeBlock getBuilderValueExpression() {
        if(super.builderValueExpression) {
            return super.builderValueExpression
        }
        if(readTypeName in [TypeName.BYTE, TypeName.CHAR, TypeName.SHORT, TypeName.INT, TypeName.LONG, TypeName.FLOAT, TypeName.DOUBLE]) {
            return CodeBlock.of('$T.getByCode($L)', typeName, name)
        }
        return null
    }

    @Override
    CodeBlock getDeclareAndReadCode() {
        return CodeBlock.builder()
            .addStatement('$T $L = $L', enumClassName, name, readCode)
            .build()
    }

    @Override
    CodeBlock getReadCode() {
        return CodeBlock.of("\$1T.getByCode(in.read${fromUpperUnderscoreToUpperCamel(x11Type)}())", enumTypeName)
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
                actualSize = CodeBlock.of('1')
                break
            case 'INT16':
            case 'CARD16':
                actualSize = CodeBlock.of('2')
                break
            case 'INT32':
            case 'CARD32':
            case 'float':
            case 'fd':
                actualSize = CodeBlock.of('4')
                break
            case 'CARD64':
            case 'double':
                actualSize = CodeBlock.of('8')
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
        if(bitcaseInfo) {
            return Optional.empty()
        }
        switch(x11Type) {
            case 'BOOL':
            case 'byte':
            case 'BYTE':
            case 'INT8':
            case 'CARD8':
            case 'char':
            case 'void':
                return Optional.of(1)
            case 'INT16':
            case 'CARD16':
                return Optional.of(2)
            case 'INT32':
            case 'CARD32':
            case 'float':
            case 'fd':
                return Optional.of(4)
            case 'CARD64':
            case 'double':
                return Optional.of(8)
        }
        throw new UnsupportedOperationException("type not supported $x11Type")
    }
}
