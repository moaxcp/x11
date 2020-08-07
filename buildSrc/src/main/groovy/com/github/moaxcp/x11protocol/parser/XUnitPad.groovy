package com.github.moaxcp.x11protocol.parser

class XUnitPad implements XUnit {
    int bytes

    @Override
    JavaPad getJavaUnit() {
        return new JavaPad(bytes:bytes)
    }
}
