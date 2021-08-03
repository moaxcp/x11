package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

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
        JavaObjectType propertyJavaType = x11Field.resolvedType.javaType
        List<JavaReadParameter> readParams = propertyJavaType.readParameters
        if(readParams) {
            String joined = readParams*.name.join(', ')
            return CodeBlock.of('$T.read$L($L, in)', typeName, typeName.simpleName(), joined)
        }

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
            return CodeBlock.of('($T.$L.isEnabled($L) ? $L : 0)', bitcaseInfo.enumType, bitcaseInfo.enumItem, bitcaseInfo.maskField.getExpression(TypeName.INT), actualSize)
        }
        return actualSize
    }

    @Override
    Optional<Integer> getFixedSize() {
        return x11Field.resolvedType.javaType.getFixedSize()
    }
}
