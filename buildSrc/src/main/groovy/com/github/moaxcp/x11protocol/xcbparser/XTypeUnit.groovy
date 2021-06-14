package com.github.moaxcp.x11protocol.xcbparser

interface XTypeUnit {
    List<JavaType> getJavaType()
    JavaProperty getJavaProperty(JavaType javaType, XUnitField field)
    JavaListProperty getJavaProperty(JavaType javaType, XUnitListField field)
}