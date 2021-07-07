package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.*

import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.getErrorTypeName
import static com.github.moaxcp.x11protocol.generator.Conventions.getEventTypeName
import static com.github.moaxcp.x11protocol.generator.Conventions.getJavaName

class JavaEvent extends JavaObjectType {
    int number
    boolean genericEvent
    int genericEventNumber

    JavaEvent(Map map) {
        super(map)
        number = map.number
        genericEvent = map.genericEvent
        genericEventNumber = map.genericEventNumber ?: -1
    }

    static JavaEvent javaEvent(XTypeEvent event) {
        ClassName superType = ClassName.get(event.basePackage, 'XEvent')
        if(event.genericEvent) {
            superType = ClassName.get(event.basePackage, 'XGenericEvent')
        }
        JavaEvent javaEvent = new JavaEvent(
            result: event.result,
            superTypes: event.superTypes + superType,
            basePackage: event.basePackage,
            javaPackage: event.javaPackage,
            className:getEventTypeName(event.javaPackage, event.name),
            number: event.number
        )
        return setProtocol(event, javaEvent)
    }

    static JavaEvent javaEvent(XTypeEvent event, String subType) {
        ClassName eventClass = getErrorTypeName(event.javaPackage, event.name + getJavaName(subType))
        ClassName superType = getErrorTypeName(event.javaPackage, event.name)

        JavaEvent javaType = new JavaEvent(
            result: event.result,
            superTypes: event.superTypes + superType,
            xUnitSubtype: subType,
            basePackage: event.basePackage,
            javaPackage: event.javaPackage,
            className: eventClass,
            number: event.number
        )

        return setProtocol(event, javaType)
    }

    private static JavaEvent setProtocol(XTypeEvent event, JavaEvent javaEvent) {
        javaEvent.protocol = event.toJavaProtocol(javaEvent)
        JavaProperty p = javaEvent.getJavaProperty('NUMBER')
        p.constantField = true
        p.writeValueExpression = CodeBlock.of('sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode()')
        if (javaEvent.fixedSize && javaEvent.fixedSize.get() < 32) {
            javaEvent.protocol.add(new JavaPad(bytes: 32 - javaEvent.fixedSize.get()))
        }

        if (event.genericEvent) {
            javaEvent.genericEvent = true
            javaEvent.genericEventNumber = event.genericEventNumber
            JavaProperty l = javaEvent.getJavaProperty('length')
            l.writeValueExpression = CodeBlock.of('getLength() - 32')
            if (!(javaEvent.protocol[1] instanceof JavaReadParameter)) {
                throw new IllegalStateException("First field must be a JavaReadParameter")
            }
            javaEvent.getJavaProperty('extension').readParam = true
            javaEvent.getJavaProperty('sequenceNumber').readParam = true
            javaEvent.getJavaProperty('length').readParam = true
            javaEvent.getJavaProperty('eventType').readParam = true
        }
        return javaEvent
    }

    @Override
    void addFields(TypeSpec.Builder typeBuilder) {
        typeBuilder.addField(FieldSpec.builder(TypeName.BYTE, 'firstEventOffset', Modifier.PRIVATE)
            .build())
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
            .addStatement('return (byte) (firstEventOffset + NUMBER)')
            .build())

        typeBuilder.addMethod(MethodSpec.methodBuilder('getNumber')
                .addAnnotation(Override)
                .returns(TypeName.BYTE)
                .addModifiers(Modifier.PUBLIC)
                .addStatement('return NUMBER')
                .build())

        super.addMethods(typeBuilder)
    }

    @Override
    void addReadParameters(MethodSpec.Builder methodBuilder) {
        methodBuilder.addParameter(ParameterSpec.builder(TypeName.BYTE, 'firstEventOffset').build())
        methodBuilder.addParameter(ParameterSpec.builder(TypeName.BOOLEAN, 'sentEvent').build())
        super.addReadParameters(methodBuilder)
    }

    @Override
    void addReadStatements(MethodSpec.Builder methodBuilder) {
        if(lastListNoLength) {
            if(genericEvent) {
                methodBuilder.addStatement('int javaStart = 10')
            } else {
                methodBuilder.addStatement('int javaStart = 1')
            }
            protocol.eachWithIndex { it, i ->
                if(!it.readProtocol
                        || (it instanceof JavaProperty && it.bitcaseInfo)) {
                    return
                }
                methodBuilder.addCode(it.declareAndReadCode)
                if(i != protocol.size() - 1) {
                    methodBuilder.addStatement('javaStart += $L', it.getSizeExpression())
                }
            }
        } else {
            super.addReadStatements(methodBuilder)
        }
        if(fixedSize && fixedSize.get() < 32) {
            methodBuilder.addStatement('in.readPad($L)', 32 - fixedSize.get())
        }
    }

    @Override
    void addBuilderStatement(MethodSpec.Builder methodBuilder, CodeBlock... fields) {
        CodeBlock.Builder startBuilder = CodeBlock.builder().addStatement('javaBuilder.$1L($1L)', 'sentEvent')
            .addStatement('javaBuilder.$1L($1L)', 'firstEventOffset')
        super.addBuilderStatement(methodBuilder, startBuilder.build())
        if(!fixedSize) {
            methodBuilder.beginControlFlow('if(javaBuilder.getSize() < 32)')
            methodBuilder.addStatement('in.readPad(32 - javaBuilder.getSize())')
            methodBuilder.endControlFlow()
            return
        }

        if(fixedSize && fixedSize.get() % 4 == 0) {
            return
        }
        methodBuilder.addStatement('in.readPadAlign(javaBuilder.getSize())')
    }

    @Override
    void addWriteStatements(MethodSpec.Builder methodBuilder) {
        super.addWriteStatements(methodBuilder)
        if(fixedSize && fixedSize.get() % 4 == 0) {
            return
        }
        methodBuilder.addStatement('out.writePadAlign(getSize())')
    }
}
