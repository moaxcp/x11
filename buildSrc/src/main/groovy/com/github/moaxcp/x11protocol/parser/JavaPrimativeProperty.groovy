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
                    .addParameter(maskTypeName, 'mask')
                    .addStatement('$1L = mask.enableFor($1L)', name)
                    .build(),
                MethodSpec.methodBuilder("${name}Disable")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(maskTypeName, 'mask')
                    .addStatement('$1L = mask.disableFor($1L)', name)
                    .build()
            ]
        }
        return []
    }

    @Override
    CodeBlock getReadCode() {
        return declareAndInitializeTo("in.read${fromUpperToUpperCamel(x11Primative)}()")
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.of("out.write${fromUpperToUpperCamel(x11Primative)}($name)")
    }

    static JavaPrimativeProperty javaPrimativeProperty(XUnitField field) {
        XTypeResolved resolvedType = field.resolvedType
        if(!x11Primatives.contains(resolvedType.name)) {
            throw new IllegalArgumentException("Could not find ${resolvedType.name} in primative types $x11Primatives")
        }
        TypeName maskTypeName = null
        if(field.maskType) {
            maskTypeName = x11PrimativeToJavaTypeName(field.resolvedMaskType.name)
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
