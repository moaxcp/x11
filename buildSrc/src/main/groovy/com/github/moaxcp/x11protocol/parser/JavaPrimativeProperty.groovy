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
    String name
    String x11Primative
    TypeName memberTypeName
    TypeName maskTypeName
    boolean readOnly
    boolean localOnly
    String lengthOfField

    @Override
    TypeName getTypeName() {
        return memberTypeName
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
        return declareAndInitializeTo("in.read${fromUpperUnderscoreToUpperCamel(x11Primative)}()")
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
                .addStatement("out.write${fromUpperUnderscoreToUpperCamel(x11Primative)}($name)")
                .build()
        }
        return CodeBlock.builder().addStatement("out.write${fromUpperUnderscoreToUpperCamel(x11Primative)}($name)").build()
    }

    @Override
    CodeBlock getSize() {
        if(memberTypeName == TypeName.BOOLEAN) {
            return CodeBlock.of('1')
        }
        if(memberTypeName == TypeName.BYTE) {
            return CodeBlock.of('1')
        }
        if(memberTypeName == TypeName.SHORT) {
            return CodeBlock.of('2')
        }
        if(memberTypeName == TypeName.CHAR) {
            return CodeBlock.of('1')
        }
        if(memberTypeName == TypeName.INT) {
            return CodeBlock.of('4')
        }
        if(memberTypeName == TypeName.LONG) {
            return CodeBlock.of('8')
        }
        throw new UnsupportedOperationException("type not supported $memberTypeName")
    }

    static JavaPrimativeProperty javaPrimativeProperty(XUnitField field) {
        XType resolvedType = field.resolvedType
        if(!x11Primatives.contains(resolvedType.name)) {
            throw new IllegalArgumentException("Could not find ${resolvedType.name} in primative types $x11Primatives")
        }
        TypeName maskTypeName = null
        if(field.maskType) {
            XTypeEnum resolvedMaskType = field.resolvedMaskType
            maskTypeName = getEnumTypeName(resolvedMaskType.javaPackage, resolvedMaskType.name)
        }

        String x11Primative = resolvedType.name

        return new JavaPrimativeProperty(
            name:convertX11VariableNameToJava(field.name),
            x11Primative:x11Primative,
            memberTypeName:x11PrimativeToJavaTypeName(resolvedType.name),
            maskTypeName:maskTypeName,
            readOnly: field.readOnly
        )
    }
}
