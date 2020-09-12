package com.github.moaxcp.x11protocol.parser

class XUnitPadAlign implements XUnit {
    int align

    @Override
    JavaPadAlign getJavaUnit(JavaType javaType) {
        return new JavaPadAlign(javaType: javaType, xUnit: this, align: align)
    }
}