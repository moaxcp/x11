package com.github.moaxcp.x11protocol.parser

class XUnitPadAlign implements XUnit {
    int align
    JavaListProperty list

    @Override
    JavaPadAlign getJavaUnit() {
        return new JavaPadAlign(align: align, list: list)
    }
}
