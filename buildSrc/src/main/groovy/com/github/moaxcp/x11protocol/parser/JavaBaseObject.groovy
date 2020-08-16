package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

abstract class JavaBaseObject implements JavaType {
    String basePackage
    String javaPackage
    String simpleName
    ClassName superType
    ClassName className
    List<JavaUnit> protocol

    JavaProperty getField(String name) {
        protocol.find {
            it.name == name
        }
    }

    boolean hasFields() {
        protocol.find {
            it instanceof JavaProperty
        }
    }

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
            .addModifiers(Modifier.PUBLIC)
            .addFields(fields)
            .addMethod(readMethod)
            .addMethod(writeMethod)
            .addMethods(methods)
        if(superType) {
            typeSpec.addSuperinterface(superType)
        }
        if(hasFields()) {
            typeSpec.addAnnotation(ClassName.get('lombok', 'Data'))
                .addAnnotation(ClassName.get('lombok', 'AllArgsConstructor'))
                .addAnnotation(ClassName.get('lombok', 'NoArgsConstructor'))
        }

        typeSpec.build()
    }

    abstract MethodSpec getReadMethod()
    abstract MethodSpec getWriteMethod()
}
