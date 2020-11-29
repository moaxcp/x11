package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.*
import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.getEventJavaName
import static com.github.moaxcp.x11protocol.generator.Conventions.getEventTypeName

class JavaEvent extends JavaObjectType {
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
        javaEvent.protocol = event.toJavaProtocol(javaEvent)
        JavaProperty p = javaEvent.getJavaProperty('NUMBER')
        p.constantField = true
        p.writeValueExpression = CodeBlock.of('sentEvent ? 0b10000000 & NUMBER : NUMBER')
        return javaEvent
    }

    @Override
    void addFields(TypeSpec.Builder typeBuilder) {
        typeBuilder.addField(FieldSpec.builder(TypeName.BOOLEAN, 'sentEvent', Modifier.PRIVATE)
            .build())
        super.addFields(typeBuilder)
    }

    @Override
    void addMethods(TypeSpec.Builder typeBuilder) {
        typeBuilder.addMethod(MethodSpec.methodBuilder('getResponseCode')
            .addAnnotation(Override)
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
    void addWriteStatements(MethodSpec.Builder methodBuilder) {
        super.addWriteStatements(methodBuilder)
        //todo could be optimized if each JavaUnit could return the int size and if the size is static (no lists/switch fields)
        methodBuilder.beginControlFlow('if(getSize() < 32)')
            .addStatement('out.writePad(32 - getSize())')
            .endControlFlow()
    }

    @Override
    void addBuilderStatement(MethodSpec.Builder methodBuilder, CodeBlock... fields) {
        CodeBlock sentEvent = CodeBlock.builder().addStatement('javaBuilder.$L($L)', 'sentEvent', 'sentEvent').build()
        super.addBuilderStatement(methodBuilder, sentEvent)
        //could be optimized if each JavaUnit could return the int size and if the size is static (no lists/switch fields)
        methodBuilder.beginControlFlow('if(javaBuilder.getSize() < 32)')
            .addStatement('in.readPad(32 - javaBuilder.getSize())')
            .endControlFlow()
    }
}
