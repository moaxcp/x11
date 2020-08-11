package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec

abstract class JavaBaseObject implements JavaType {
    String basePackage
    String simpleName
    ClassName superType
    ClassName className
    List<JavaUnit> protocol

    @Override
    TypeSpec getTypeSpec() {
        List<FieldSpec> fields = protocol.findAll {
            it instanceof JavaProperty
        }.collect { JavaProperty it ->
            it.member
        }
        List<MethodSpec> methods = protocol.findAll {
            it instanceof JavaProperty
        }.collect { JavaProperty it ->
            it.methods
        }.flatten()
        TypeSpec.Builder typeSpec = TypeSpec.classBuilder(className)
            .addAnnotation(ClassName.get('lombok', 'Data'))
            .addFields(fields)
            .addMethod(readMethod)
            .addMethod(writeMethod)
            .addMethods(methods)
        if(superType) {
            typeSpec.addSuperinterface(superType)
        }

        typeSpec.build()
    }

    abstract MethodSpec getReadMethod()
    abstract MethodSpec getWriteMethod()
}
