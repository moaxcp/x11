package com.github.moaxcp.x11protocol.generator

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec

import javax.lang.model.element.Modifier

class BaseClassGenerator {
    File outputSrc
    String basePackage

    void generate() {
        generateIntValue()
        generateValueMask()
    }

    void generateIntValue() {
        TypeSpec intValue = TypeSpec.interfaceBuilder('IntValue')
            .addModifiers(Modifier.PUBLIC)
            .addMethod(MethodSpec.methodBuilder('getValue')
                .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
                .returns(TypeName.INT)
                .build())
            .build()
        JavaFile.builder(basePackage, intValue).build().writeTo(outputSrc)
    }

    void generateValueMask() {
        TypeSpec valueMask = TypeSpec.interfaceBuilder('ValueMask')
            .addModifiers(Modifier.PUBLIC)
            .addMethod(MethodSpec.methodBuilder('getMask')
                .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
                .returns(TypeName.INT)
                .build())
            .addMethod(MethodSpec.methodBuilder('mask')
                .addParameter(TypeName.INT, 'value')
                .addModifiers(Modifier.PUBLIC, Modifier.DEFAULT)
                .returns(TypeName.INT)
                .addStatement('return value | getMask()')
                .build())
            .build()
        JavaFile.builder(basePackage, valueMask).build().writeTo(outputSrc)
    }
}
