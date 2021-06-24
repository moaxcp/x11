package com.github.moaxcp.x11protocol.xcbparser


import static com.github.moaxcp.x11protocol.xcbparser.JavaPrimativeListProperty.javaPrimativeListProperty

class XTypePrimative extends XType {

    XTypePrimative(Map map) {
        super(map)
    }

    @Override
    List<String> getSubTypeNames() {
        return []
    }

    @Override
    JavaType getJavaType() {
        throw new UnsupportedOperationException("primatives")
    }

    @Override
    boolean hasSubTypes() {
        return false
    }

    @Override
    JavaType getSubType(String subType) {
        throw new UnsupportedOperationException("primatives")
    }

    @Override
    List<JavaType> getSubTypes() {
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
