package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock 

class JavaTypeProperty extends JavaProperty {
    
    JavaTypeProperty(JavaType javaType, XUnitField field) {
        super(javaType, field)
    }

    static JavaTypeProperty javaTypeProperty(JavaType javaType, XUnitField field) {
        return new JavaTypeProperty(javaType, field)
    }

    @Override
    ClassName getTypeName() {
        return x11Field.resolvedType.javaType.className
    }

    @Override
    boolean isNonNull() {
        return true
    }

    @Override
    CodeBlock getDeclareAndReadCode() {
        return declareAndInitializeTo(readCode)
    }

    @Override
    CodeBlock getReadCode() {
        return CodeBlock.of('$T.read$L(in)', typeName, typeName.simpleName())
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.builder()
            .addStatement('$L.write(out)', name)
            .build()
    }

    @Override
    CodeBlock getSizeExpression() {
        return CodeBlock.of('$L.getSize()', name)
    }

    @Override
    Optional<Integer> getFixedSize() {
        return x11Field.resolvedType.javaType.getFixedSize()
    }
}
