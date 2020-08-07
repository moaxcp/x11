package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

class JavaObjectProperty extends JavaProperty {
    String name
    TypeName typeName
    boolean readOnly
    boolean localOnly

    static JavaObjectProperty javaObjectProperty(XUnitField field) {
        return null
    }

    @Override
    CodeBlock getReadCode() {
        return null
    }

    @Override
    CodeBlock getWriteCode() {
        return null
    }
}
