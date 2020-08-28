package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.*
import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.getEventJavaName
import static com.github.moaxcp.x11protocol.generator.Conventions.getEventTypeName

class JavaEvent extends JavaBaseObject {
    int number

    static JavaEvent javaEvent(XTypeEvent event) {
        String simpleName = getEventJavaName(event.name)

        JavaEvent javaEvent = new JavaEvent(
            superTypes: event.superTypes + ClassName.get(event.basePackage, 'XEvent'),
            basePackage: event.basePackage,
            javaPackage: event.javaPackage,
            simpleName:simpleName,
            className:getEventTypeName(event.javaPackage, event.name),
            number: event.number
        )
        javaEvent.protocol = [new JavaPrimativeProperty(
            name: 'eventDetail',
            x11Primative: 'CARD8',
            memberTypeName: TypeName.BYTE
        ),
        new JavaPrimativeProperty(
            name: 'sequenceNumber',
            x11Primative: 'CARD16',
            memberTypeName: TypeName.SHORT
        )] + event.toJavaProtocol(javaEvent)
        return javaEvent
    }

    @Override
    void addFields(TypeSpec.Builder typeBuilder) {
        typeBuilder.addField(FieldSpec.builder(TypeName.BYTE, 'NUMBER', Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
            .initializer('$L', number)
            .build())
        typeBuilder.addField(FieldSpec.builder(TypeName.BOOLEAN, 'sentEvent', Modifier.PRIVATE)
            .build())
        super.addFields(typeBuilder)
    }

    @Override
    void addMethods(TypeSpec.Builder typeBuilder) {
        typeBuilder.addMethod(MethodSpec.methodBuilder('getNumber')
            .returns(TypeName.BYTE)
            .addModifiers(Modifier.PUBLIC)
            .addStatement('return NUMBER')
            .build())

        super.addMethods(typeBuilder)
    }

    @Override
    void addReadParameters(MethodSpec.Builder methodBuilder) {
        super.addReadParameters(methodBuilder)
        methodBuilder.addParameter(ParameterSpec.builder(TypeName.BOOLEAN, 'sentEvent').build())
    }

    @Override
    void addSettersCode(CodeBlock.Builder codeBuilder) {
        codeBuilder.addStatement('$L.$L($L)', 'javaObject', 'setSentEvent', 'sentEvent')
        super.addSettersCode(codeBuilder)
    }
}
