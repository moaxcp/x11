package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.CodeBlock

class JavaNotifyDataUnionProperty extends JavaTypeProperty {
    JavaNotifyDataUnionProperty(JavaType javaType, XUnitField field) {
        super(javaType, field)

    }
    static javaNotifyDataUnionProperty(JavaType javaType, XUnitField field) {
        return new JavaNotifyDataUnionProperty(javaType, field)
    }

    @Override
    CodeBlock getReadCode() {
        return CodeBlock.of('$T.read$L(in, subCode)', typeName, typeName.simpleName())
    }
}
