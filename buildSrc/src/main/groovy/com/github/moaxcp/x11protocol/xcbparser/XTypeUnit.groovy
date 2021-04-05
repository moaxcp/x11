package com.github.moaxcp.x11protocol.xcbparser

interface XTypeUnit {
    JavaType getJavaType()
    JavaProperty getJavaProperty(JavaType javaType, XUnitField field)
    JavaListProperty getJavaProperty(JavaType javaType, XUnitListField field)
}