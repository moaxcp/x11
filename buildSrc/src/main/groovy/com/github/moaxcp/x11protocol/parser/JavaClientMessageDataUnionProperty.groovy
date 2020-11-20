package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock

class JavaClientMessageDataUnionProperty extends JavaTypeProperty {
    JavaClientMessageDataUnionProperty(JavaType javaType, XUnitField field) {
        super(javaType, field)

    }
    static javaClientMessageDataUnionProperty(JavaType javaType, XUnitField field) {
        return new JavaClientMessageDataUnionProperty(javaType, field)
    }

    @Override
    CodeBlock getReadCode() {
        return CodeBlock.of('$T.read$L(in, format)', typeName, typeName.simpleName())
    }
}
