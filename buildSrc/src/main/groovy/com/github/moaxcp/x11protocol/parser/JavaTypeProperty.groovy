package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock

import static com.github.moaxcp.x11protocol.generator.Conventions.convertX11VariableNameToJava

class JavaTypeProperty extends JavaProperty {
    String name
    ClassName typeName
    boolean readOnly
    boolean localOnly

    static JavaTypeProperty javaTypeProperty(XUnitField field) {
        JavaType javaType = field.resolvedType.javaType
        return new JavaTypeProperty(
            name: convertX11VariableNameToJava(field.name),
            typeName: javaType.className,
            readOnly: field.readOnly
        )
    }

    @Override
    CodeBlock getReadCode() {
        return declareAndInitializeTo(CodeBlock.of('$T.read$L(in)', typeName, typeName.simpleName()))
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.builder()
            .addStatement('$L.write(out)', name)
            .build()
    }

    @Override
    CodeBlock getSize() {
        return CodeBlock.of('$L.getSize()', name)
    }
}
