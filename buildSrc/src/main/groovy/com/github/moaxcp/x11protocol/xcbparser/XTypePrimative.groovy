package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.ClassName

import static com.github.moaxcp.x11protocol.xcbparser.JavaPrimativeListProperty.javaPrimativeListProperty

class XTypePrimative extends XType {

    XTypePrimative(Map map) {
        super(map)
    }

    @Override
    Optional<ClassName> getCaseSuperName() {
        return Optional.empty()
    }

    @Override
    List<String> getCaseNames() {
        return []
    }

    @Override
    List<String> getCaseClassNames() {
        return []
    }

    @Override
    List<JavaType> getJavaType() {
        throw new UnsupportedOperationException("primatives")
    }

    @Override
    JavaPrimativeProperty getJavaProperty(JavaType javaType, XUnitField field) {
        return new JavaPrimativeProperty(javaType, field)
    }

    @Override
    JavaListProperty getJavaProperty(JavaType javaType, XUnitListField field) {
        return javaPrimativeListProperty(javaType, field)
    }
}
