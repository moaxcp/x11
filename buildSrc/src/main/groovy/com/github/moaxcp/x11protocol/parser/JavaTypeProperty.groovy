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
    CodeBlock getDeclareAndReadCode() {
        return declareAndInitializeTo(readCode)
    }

    @Override
    CodeBlock getReadCode() {
        return CodeBlock.of('$T.read$L(in)', typeName, typeName.simpleName())
    }

    @Override
    void addWriteCode(CodeBlock.Builder code) {
        code.addStatement('$L.write(out)', name)
    }

    @Override
    CodeBlock getSizeExpression() {
        CodeBlock actualSize = CodeBlock.of('$L.getSize()', name)
        if(bitcaseInfo) {
            return CodeBlock.of('(is$LEnabled($T.$L) ? $L : 0)', bitcaseInfo.maskField.capitalize(), bitcaseInfo.enumType, bitcaseInfo.enumItem, actualSize)
        }
        return actualSize
    }

    @Override
    Optional<Integer> getFixedSize() {
        return x11Field.resolvedType.javaType.getFixedSize()
    }
}
