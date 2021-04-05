package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.xcbparser.expression.EmptyExpression
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.getStructTypeName

class JavaTypeListProperty extends JavaListProperty {

    JavaTypeListProperty(JavaType javaType, XUnitListField field) {
        super(javaType, field)
    }

    static JavaTypeListProperty javaTypeListProperty(JavaType javaType, XUnitListField field) {
        return new JavaTypeListProperty(javaType, field)
    }

    String getBasePackage() {
        return javaType.basePackage
    }

    @Override
    ClassName getBaseTypeName() {
        XType type = x11Field.resolvedType
        if(type instanceof XTypeStruct) {
            return getStructTypeName(type.javaPackage, type.name)
        } else { //else Request/Reply/Event
            throw new UnsupportedOperationException("not supported ${type.name}")
        }
    }

    @Override
    TypeName getTypeName() {
        return ParameterizedTypeName.get(ClassName.get(List), baseTypeName)
    }

    @Override
    CodeBlock getDeclareAndReadCode() {
        if(lengthExpression instanceof EmptyExpression) {
            return CodeBlock.builder()
                .addStatement('$1T $2L = new $3T<>(length - javaStart)', typeName, name, ArrayList.class)
                .beginControlFlow('while(javaStart < Short.toUnsignedInt(length) * 4)')
                .addStatement('$1T baseObject = $1T.read$2L(in)', baseTypeName, baseTypeName.simpleName())
                .addStatement('$L.add(baseObject)', name)
                .addStatement('javaStart += baseObject.getSize()')
                .endControlFlow()
                .build()
        }
        return CodeBlock.builder()
            .addStatement('$1T $2L = new $3T<>($4L)', typeName, name, ArrayList.class, lengthExpression.getExpression(TypeName.INT))
            .beginControlFlow('for(int i = 0; i < $L; i++)', lengthExpression.expression)
            .addStatement('$L.add($T.read$L(in))', name, baseTypeName, baseTypeName.simpleName())
            .endControlFlow()
            .build()
    }

    @Override
    CodeBlock getReadCode() {
        throw new IllegalStateException()
    }

    @Override
    void addWriteCode(CodeBlock.Builder code) {
        code.beginControlFlow('for($T t : $L)', baseTypeName, name)
        code.addStatement('t.write(out)')
        code.endControlFlow()
    }

    @Override
    CodeBlock getSizeExpression() {
        CodeBlock actualSize = CodeBlock.of('$T.sizeOf($L)', ClassName.get(basePackage, 'XObject'), name)
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
