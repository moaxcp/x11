package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import groovy.transform.ToString
import javax.lang.model.element.Modifier
import lombok.Setter

@ToString(includeSuperProperties = true, includePackage = false, includes = ['name', 'type'])
class XField extends ResolvableVariable implements PropertyXUnit {
    boolean readOnly = false
    boolean lengthField = false

    @Override
    FieldSpec getMember() {
        FieldSpec.Builder builder = FieldSpec.builder(resolvedType.javaType, javaName)
            .addModifiers(Modifier.PRIVATE)
        if(readOnly) {
            builder.addAnnotation(
                AnnotationSpec.builder(Setter)
                    .addMember('value', CodeBlock.of('AccessLevel.NONE'))
                    .build())
        }
        builder.build()
    }

    @Override
    CodeBlock getReadCode() {
        CodeBlock.Builder block = CodeBlock.builder()
        XType type = resolvedType
        switch(type.type) {
            case 'primative':
                switch(type.name) {
                    case 'CARD32':
                        block.addStatement("$javaName = in.readCard32()")
                }
                break
            case 'xid':
            case 'xidunion':
                block.addStatement("$javaName = in.readCard32()")
        }
        return block.build()
    }

    @Override
    CodeBlock getWriteCode() {
        CodeBlock.Builder block = CodeBlock.builder()
        XType type = resolvedType
        switch(type.type) {
            case 'primative':
                switch(type.name) {
                    case 'CARD32':
                        block.addStatement("out.writeCard32($javaName)")
                }
                break
            case 'xid':
            case 'xidunion':
                block.addStatement("out.writeCard32($javaName)")
        }
        return block.build()
    }
}
