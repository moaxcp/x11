package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.*
/**
 * for converting fields that have an enum set
 */
class JavaEnumProperty extends JavaProperty {

    JavaEnumProperty(JavaType javaType, XUnitField field) {
        super(javaType, field)
    }

    static JavaEnumProperty javaEnumProperty(JavaType javaType, XUnitField field) {
        return new JavaEnumProperty(javaType, field)
    }

    @Override
    TypeName getTypeName() {
        return memberTypeName
    }

    TypeName getMemberTypeName() {
        getEnumTypeName(x11Field.resolvedEnumType.javaPackage, x11Field.resolvedEnumType.name)
    }

    TypeName getIoTypeName() {
        x11PrimativeToStorageTypeName(x11Field.resolvedType.name)
    }
    
    @Override
    boolean isNonNull() {
        return true
    }

    @Override
    TypeName getReadTypeName() {
        if(super.readTypeName) {
            return super.readTypeName
        }
        return ioTypeName
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
            .addStatement(readCode)
            .build()
    }

    @Override
    CodeBlock getReadCode() {
        return CodeBlock.of("\$1T \$2L = \$1T.getByCode(in.read${fromUpperUnderscoreToUpperCamel(x11Type)}())", memberTypeName, name)
    }

    @Override
    void addWriteCode(CodeBlock.Builder code) {
        if(ioTypeName != TypeName.INT) {
            code.addStatement("out.write${fromUpperUnderscoreToUpperCamel(x11Type)}((\$T) \$L.getValue())", ioTypeName, name)
        } else {
            code.addStatement("out.write${fromUpperUnderscoreToUpperCamel(x11Type)}(\$L.getValue())", name)
        }
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
                return CodeBlock.of('1')
            case 'INT16':
            case 'CARD16':
                return CodeBlock.of('2')
            case 'INT32':
            case 'CARD32':
            case 'float':
            case 'fd':
                return CodeBlock.of('4')
            case 'CARD64':
            case 'double':
                return CodeBlock.of('8')
        }
        throw new UnsupportedOperationException("type not supported $x11Type")
    }

    @Override
    Optional<Integer> getFixedSize() {
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
