package com.github.moaxcp.x11protocol.parser

interface XTypeUnit {
    JavaType getJavaType()
    JavaProperty getJavaProperty(XUnitField field)
    JavaListProperty getJavaListProperty(JavaType javaType, XUnitListField field)
}