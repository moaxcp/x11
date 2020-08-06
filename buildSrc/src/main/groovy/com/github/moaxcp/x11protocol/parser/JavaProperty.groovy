package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier
import lombok.Setter

interface JavaProperty extends JavaUnit {
    String getName()
    TypeName getTypeName()
    default FieldSpec getMember() {
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

    default String getSetterName() {
        return "set${name.capitalize()}"
    }

    default String getGetterName() {
        return "get${name.capitalize()}"
    }

    default List<MethodSpec> getMethods() {
        return []
    }

    boolean isReadOnly()
    void setReadOnly(boolean readOnly)
    boolean isLocalOnly()
    void setLocalOnly(boolean localOnly)

    default CodeBlock declareAndInitializeTo(String readCall) {
        return CodeBlock.of('$T $L = $L', typeName, name, readCall)
    }
}