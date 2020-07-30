package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.generator.X11Result

class X11Type {
    X11Result result
    String type //(primative, struct, union, request)
    String name //(CARD32, POINT, CreateWindow)

    String getGroup() {
        result.header
    }
}
