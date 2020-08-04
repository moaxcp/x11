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
    String getSetterName() {
        return "set${javaName.capitalize()}"
    }

    @Override
    String getGetterName() {
        return "get${javaName.capitalize()}"
    }

    @Override
    CodeBlock getReadCode() {
        XType type = resolvedType
        switch(type.type) {
            case 'primative':
                switch(type.name) {
                    case 'CARD8':
                        return declareAndInitializeTo('in.readCard8()')
                        break
                    case 'CARD32':
                        return declareAndInitializeTo("in.readCard32()")
                        break
                    default:
                        throw new IllegalArgumentException("primative ${type.name} from $type not supported")
                }
                break
            case 'xid':
            case 'xidunion':
                return declareAndInitializeTo("in.readCard32()")
                break
            default:
                throw new IllegalArgumentException("type not supported $type")
        }
    }

    CodeBlock declareAndInitializeTo(String readCall) {
        return CodeBlock.of('$T $L = $L', javaTypeName, javaName, readCall)
    }

    @Override
    CodeBlock getWriteCode() {
        XType type = resolvedType
        switch(type.type) {
            case 'primative':
                switch(type.name) {
                    case 'CARD8':
                        return CodeBlock.of("out.writeCard8($javaName)")
                    case 'CARD32':
                        return CodeBlock.of("out.writeCard32($javaName)")
                    default:
                        throw new IllegalArgumentException("primative ${type.name} from $type not supported")
                }
                break
            case 'xid':
            case 'xidunion':
                return CodeBlock.of("out.writeCard32($javaName)")
            default:
                throw new IllegalArgumentException("type not supported $type")
        }
    }
}
