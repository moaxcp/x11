package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.fromUpperUnderscoreToUpperCamel
import static com.github.moaxcp.x11protocol.generator.Conventions.x11PrimativeToJavaTypeName

class JavaPrimativeListProperty extends JavaListProperty {

    JavaPrimativeListProperty(JavaType javaType, XUnitListField field) {
        super(javaType, field)
    }

    @Override
    TypeName getBaseTypeName() {
        return x11PrimativeToJavaTypeName(x11Field.resolvedType.name)
    }

    @Override
    TypeName getTypeName() {
        return ArrayTypeName.of(baseTypeName)
    }

    static JavaPrimativeListProperty javaPrimativeListProperty(JavaType javaType, XUnitListField field) {
        return new JavaPrimativeListProperty(javaType, field)
    }

    @Override
    CodeBlock getReadCode() {
        return declareAndInitializeTo("in.read${fromUpperUnderscoreToUpperCamel(x11Type)}(${lengthExpression.expression})")
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.builder()
            .addStatement("out.write${fromUpperUnderscoreToUpperCamel(x11Type)}($name)")
            .build()
    }

    @Override
    CodeBlock getSize() {
        switch(x11Type) {
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
        throw new UnsupportedOperationException("type not supported $x11Type")
    }
}
