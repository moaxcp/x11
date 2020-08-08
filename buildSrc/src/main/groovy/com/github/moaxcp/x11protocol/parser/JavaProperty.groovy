package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier
import lombok.Setter

abstract class JavaProperty implements JavaUnit {
    abstract String getName()
    abstract TypeName getTypeName()
    FieldSpec getMember() {
        FieldSpec.Builder builder = FieldSpec.builder(typeName, name)
            .addModifiers(Modifier.PRIVATE)
        if(readOnly) {
            builder.addAnnotation(
                AnnotationSpec.builder(Setter)
                    .addMember('value', CodeBlock.of('AccessLevel.NONE'))
                    .build())
        }
        return builder.build()
    }

    String getSetterName() {
        return "set${name.capitalize()}"
    }

    String getGetterName() {
        return "get${name.capitalize()}"
    }

    List<MethodSpec> getMethods() {
        return []
    }

    abstract boolean isReadOnly()
    abstract void setReadOnly(boolean readOnly)
    abstract boolean isLocalOnly()
    abstract void setLocalOnly(boolean localOnly)

    CodeBlock declareAndInitializeTo(String readCall) {
        return CodeBlock.of('$T $L = $L', typeName, name, readCall)
    }

    CodeBlock declareAndInitializeTo(CodeBlock readCall) {
        return CodeBlock.of('$T $L = $L', typeName, name, readCall)
    }
}