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
    CodeBlock getReadCode() {
        return declareAndInitializeTo("in.readString8(${lengthExpression.getExpression(TypeName.INT)})")
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.builder()
            .addStatement("out.writeString8($name)")
            .build()
    }

    @Override
    CodeBlock getSizeExpression() {
        return CodeBlock.of('$L.length()', name)
    }
}
