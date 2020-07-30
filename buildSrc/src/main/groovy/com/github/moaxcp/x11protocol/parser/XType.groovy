package com.github.moaxcp.x11protocol.parser

class XType {
    XResult result
    String type //(primative, struct, union, request)
    String name //(CARD32, POINT, CreateWindow)

    String getGroup() {
        result.header
    }
}
