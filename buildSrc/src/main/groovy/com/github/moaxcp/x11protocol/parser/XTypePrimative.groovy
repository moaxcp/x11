package com.github.moaxcp.x11protocol.parser

import static com.github.moaxcp.x11protocol.parser.JavaPrimativeListProperty.javaPrimativeListProperty
import static com.github.moaxcp.x11protocol.parser.JavaPrimativeStringListProperty.javaPrimativeStringListProperty

class XTypePrimative extends XType {
    @Override
    JavaType getJavaType() {
        throw new UnsupportedOperationException("primatives")
    }

    @Override
    JavaPrimativeProperty getJavaProperty(JavaType javaType, XUnitField field) {
        return new JavaPrimativeProperty(javaType, field)
    }

    @Override
    JavaListProperty getJavaProperty(JavaType javaType, XUnitListField field) {
        if(field.resolvedType.name == 'char') {
            return javaPrimativeStringListProperty(javaType, field)
        }
        return javaPrimativeListProperty(javaType, field)
    }
}
