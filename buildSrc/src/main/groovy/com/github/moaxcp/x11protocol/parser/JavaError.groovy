package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.*
import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.getErrorTypeName
import static com.github.moaxcp.x11protocol.generator.Conventions.getEventJavaName

class JavaError extends JavaObjectType {
    int number

    static JavaError javaError(XTypeError error) {
        String simpleName = getEventJavaName(error.name)

        JavaError javaError = new JavaError(
            superTypes: error.superTypes + ClassName.get(error.basePackage, 'XError'),
            basePackage: error.basePackage,
            javaPackage: error.javaPackage,
            simpleName:simpleName,
            className:getErrorTypeName(error.javaPackage, error.name),
            number: error.number
        )
        javaError.protocol = [new JavaPrimativeProperty(
            javaError,
            new XUnitField(result: error.result, name: 'sequence_number', type:'CARD16')
        )] + error.toJavaProtocol(javaError)
        return javaError
    }

    @Override
    void addFields(TypeSpec.Builder typeBuilder) {
        typeBuilder.addField(FieldSpec.builder(TypeName.BYTE, 'CODE', Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
            .initializer('$L', number)
            .build())
        super.addFields(typeBuilder)
    }

    @Override
    void addMethods(TypeSpec.Builder typeBuilder) {
        typeBuilder.addMethod(MethodSpec.methodBuilder('getCode')
            .returns(TypeName.BYTE)
            .addModifiers(Modifier.PUBLIC)
            .addStatement('return CODE')
            .build())

        super.addMethods(typeBuilder)
    }

    @Override
    void addWriteStatements(MethodSpec.Builder methodBuilder) {
        methodBuilder.addStatement('out.writeCard8(0)')
        methodBuilder.addStatement('out.writeCard8(CODE)')
        super.addWriteStatements(methodBuilder)
        //could be optimized if each JavaUnit could return the int size and if the size is static (no lists/switch fields)
        methodBuilder.beginControlFlow('if(getSize() < 32)')
            .addStatement('out.writePad(32 - getSize())')
            .endControlFlow()
    }

    @Override
    void addSetterStatements(MethodSpec.Builder methodBuilder) {
        super.addSetterStatements(methodBuilder)
        //could be optimized if each JavaUnit could return the int size and if the size is static (no lists/switch fields)
        methodBuilder.beginControlFlow('if(javaObject.getSize() < 32)')
            .addStatement('in.readPad(32 - javaObject.getSize())')
            .endControlFlow()
    }
}
