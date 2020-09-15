package com.github.moaxcp.x11protocol.parser

interface XTypeUnit {
    JavaType getJavaType()
    JavaProperty getJavaProperty(JavaType javaType, XUnitField field)
    JavaListProperty getJavaProperty(JavaType javaType, XUnitListField field)
}