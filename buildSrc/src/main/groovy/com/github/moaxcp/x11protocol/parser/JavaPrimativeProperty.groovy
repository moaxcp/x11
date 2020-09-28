package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.*
/**
 * for x11 primative properties
 */
class JavaPrimativeProperty extends JavaProperty {
    String lengthOfField

    JavaPrimativeProperty(Map map) {
        super(map)
        lengthOfField = map.lengthOfField
    }
    
    JavaPrimativeProperty(JavaType javaType, XUnitField field) {
        super(javaType, field)
    }

    @Override
    TypeName getTypeName() {
        return memberTypeName
    }

    TypeName getMemberTypeName() {
        if(!x11Primatives.contains(x11Field.resolvedType.name)) {
        throw new IllegalStateException("Could not find ${x11Field.resolvedType.name} in primative types $x11Primatives")
    }
        return x11PrimativeToStorageTypeName(x11Field.resolvedType.name)
    }

    TypeName getMaskTypeName() {
        if(x11Field.maskType) {
            XType resolvedMaskType = x11Field.resolvedMaskType
            return getEnumTypeName(resolvedMaskType.javaPackage, resolvedMaskType.name)
        }
        return null
    }

    boolean isReadOnly() {
        return getMaskTypeName()
    }

    @Override
    List<MethodSpec> getMethods() {
        if(maskTypeName) {
            return [
                MethodSpec.methodBuilder("${name}Enable")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(maskTypeName, 'maskEnum')
                    .addStatement('$1L = ($2T) maskEnum.enableFor($1L)', name, memberTypeName)
                    .build(),
                MethodSpec.methodBuilder("${name}Disable")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(maskTypeName, 'maskEnum')
                    .addStatement('$1L = ($2T) maskEnum.disableFor($1L)', name, memberTypeName)
                    .build()
            ]
        }
        return []
    }

    @Override
    CodeBlock getReadCode() {
        return declareAndInitializeTo("in.read${fromUpperUnderscoreToUpperCamel(x11Type)}()")
    }

    @Override
    CodeBlock getWriteCode() {
        if(lengthOfField) {
            CodeBlock.Builder builder = CodeBlock.builder()
            if(memberTypeName != TypeName.INT) {
                builder.addStatement('$1T $2L = ($1T) $3L.length()', memberTypeName, name, lengthOfField)
            } else {
                builder.addStatement('$1T $2L = $3L.length()', memberTypeName, name, lengthOfField)
            }
            return builder
                .addStatement("out.write${fromUpperUnderscoreToUpperCamel(x11Type)}($name)")
                .build()
        }
        return CodeBlock.builder().addStatement("out.write${fromUpperUnderscoreToUpperCamel(x11Type)}($name)").build()
    }

    @Override
    CodeBlock getSizeExpression() {
        switch(x11Type) {
            case 'BOOL':
            case 'byte':
            case 'BYTE':
            case 'INT8':
            case 'CARD8':
            case 'char':
            case 'void':
                return CodeBlock.of('1')
            case 'INT16':
            case 'CARD16':
                return CodeBlock.of('2')
            case 'INT32':
            case 'CARD32':
            case 'float':
            case 'fd':
                return CodeBlock.of('4')
            case 'CARD64':
            case 'double':
                return CodeBlock.of('8')
        }
        throw new UnsupportedOperationException("type not supported $x11Primative")
    }

    @Override
    Optional<Integer> getFixedSize() {
        switch(x11Type) {
            case 'BOOL':
            case 'byte':
            case 'BYTE':
            case 'INT8':
            case 'CARD8':
            case 'char':
            case 'void':
                return Optional.of(1)
            case 'INT16':
            case 'CARD16':
                return Optional.of(2)
            case 'INT32':
            case 'CARD32':
            case 'float':
            case 'fd':
                return Optional.of(4)
            case 'CARD64':
            case 'double':
                return Optional.of(8)
        }
        throw new UnsupportedOperationException("type not supported $x11Type")
    }
}
