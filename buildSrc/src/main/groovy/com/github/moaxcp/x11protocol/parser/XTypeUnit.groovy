package com.github.moaxcp.x11protocol.parser

interface XTypeUnit {
    JavaType getJavaType()
    JavaProperty getJavaProperty(JavaType javaType, XUnitField field)
    JavaListProperty getJavaListProperty(JavaType javaType, XUnitListField field)
}