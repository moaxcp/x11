package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName 

class JavaPrimativeStringListProperty extends JavaPrimativeListProperty {
    
    JavaPrimativeStringListProperty(JavaType javaType, XUnitListField field) {
        super(javaType, field)
    }
    
    @Override
    TypeName getTypeName() {
        if(x11Field.resolvedType.name != 'char') {
            throw new IllegalArgumentException("Only char is supported. Got ${resolvedType.name}")
        }
        return ClassName.get(String.class)
    }

    static JavaPrimativeStringListProperty javaPrimativeStringListProperty(JavaType javaType, XUnitListField field) {
        return new JavaPrimativeStringListProperty(javaType, field)
    }

    @Override
    CodeBlock getDeclareAndReadCode() {
        return declareAndInitializeTo("in.readString8(${lengthExpression.getExpression(TypeName.INT)})")
    }

    @Override
    void addWriteCode(CodeBlock.Builder code) {
        code.addStatement("out.writeString8($name)")
    }

    @Override
    CodeBlock getSizeExpression() {
        CodeBlock actualSize = CodeBlock.of('$L.length()', name)
        if(bitcaseInfo) {
            return CodeBlock.of('(is$LEnabled($T.$L) ? $L : 0)', bitcaseInfo.maskField.capitalize(), bitcaseInfo.enumType, bitcaseInfo.enumItem, actualSize)
        }
        return actualSize
    }
}
