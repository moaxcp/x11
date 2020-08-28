package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock

import static com.github.moaxcp.x11protocol.generator.Conventions.convertX11VariableNameToJava

class JavaClientMessageDataUnionProperty extends JavaTypeProperty {
    static javaClientMessageDataUnionProperty(XUnitField field) {
        JavaType javaType = field.resolvedType.javaType
        return new JavaClientMessageDataUnionProperty(
            name: convertX11VariableNameToJava(field.name),
            typeName: javaType.className,
            readOnly: field.readOnly
        )
    }

    @Override
    CodeBlock getReadCode() {
        return declareAndInitializeTo(CodeBlock.of('$T.read$L(in, format)', typeName, typeName.simpleName()))
    }
}
