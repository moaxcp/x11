package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.generator.Conventions
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier
import lombok.Setter

import static com.github.moaxcp.x11protocol.generator.Conventions.fromUpperToUpperCamel
import static com.github.moaxcp.x11protocol.generator.Conventions.x11Primatives
import static java.util.Objects.requireNonNull

abstract class ResolvableProperty implements PropertyXUnit {
    XResult result
    String type
    String enumType
    String maskType
    String name
    boolean readOnly = false
    boolean localOnly = false

    @Override
    String getJavaName() {
        return Conventions.convertX11VariableNameToJava(name)
    }

    @Override
    TypeName getJavaTypeName() {
        return resolvedType.getJavaType()
    }

    XType getResolvedType() {
        return result.resolveXType(type)
    }

    TypeName getJavaEnumTypeName() {
        return resolvedTypeEnum.javaType
    }

    XType getResolvedTypeMask() {
        requireNonNull(maskType, "maskType must not be null")
        return result.resolveXType(maskType)
    }

    TypeName getJavaMaskTypeName() {
        return resolvedTypeMask.javaType
    }

    XType getResolvedTypeEnum() {
        requireNonNull(enumType, "enumType must not be null")
        return result.resolveXType(enumType)
    }

    @Override
    FieldSpec getMember() {
        TypeName typeName = enumType ? javaEnumTypeName : javaTypeName
        FieldSpec.Builder builder = FieldSpec.builder(typeName, javaName)
            .addModifiers(Modifier.PRIVATE)
        if(readOnly) {
            builder.addAnnotation(
                AnnotationSpec.builder(Setter)
                    .addMember('value', CodeBlock.of('AccessLevel.NONE'))
                    .build())
        }
        return builder.build()
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
        XType type = enumType ? resolvedTypeEnum : resolvedType
        switch(type.type) {
            case 'primative':
                if(x11Primatives.contains(type.name)) {
                    return declareAndInitializeTo("in.read${fromUpperToUpperCamel(type.name)}()")
                }
                throw new IllegalArgumentException("primative ${type.name} from $type not supported")
                break
            case 'enum':
                return CodeBlock.of("\$1T \$2L = \$1T.getByCode(in.read${fromUpperToUpperCamel(resolvedType.name)}())",
                    javaEnumTypeName, javaName)
                break
            case 'xid':
            case 'xidunion':
                return declareAndInitializeTo("in.readCard32()")
                break
            default:
                throw new IllegalArgumentException("type not supported $type")
        }
    }

    private CodeBlock declareAndInitializeTo(String readCall) {
        return CodeBlock.of('$T $L = $L', javaTypeName, javaName, readCall)
    }

    @Override
    CodeBlock getWriteCode() {
        XType type = enumType ? resolvedTypeEnum : resolvedType
        switch(type.type) {
            case 'primative':
                if(x11Primatives.contains(type.name)) {
                    return writePrimativeBlock(type.name)
                }
                throw new IllegalArgumentException("primative ${type.name} from $type not supported")
            case 'enum':
                if(javaTypeName != TypeName.INT) {
                    return CodeBlock.of("out.write${fromUpperToUpperCamel(resolvedType.name)}((\$T) \$L.getValue()))",
                        javaTypeName, javaName)
                } else {
                    return CodeBlock.of("out.write${fromUpperToUpperCamel(resolvedType.name)}(\$L.getValue()))",
                        javaName)
                }
                
                break
            case 'xid':
            case 'xidunion':
                return writePrimativeBlock('CARD32')
            default:
                throw new IllegalArgumentException("type not supported $type")
        }
    }

    CodeBlock writePrimativeBlock(String type) {
        type = fromUpperToUpperCamel(type)
        return CodeBlock.of("out.write$type($javaName)")
    }
}
