package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.generator.X11Result

class X11Field {
    X11Result x11Result
    String type
    String name
    String mask
    String enumType

    X11Type getResolvedType() {
        result.resolveX11Type(type)
    }
}
