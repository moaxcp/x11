package com.github.moaxcp.x11protocol.generator

import spock.lang.Specification

class X11ResultSpec extends Specification {
    def 'glx resolveType'() {
        given:
        X11Result result = X11Parser.parse(new File('../src/main/xcbXmls/glx.xml'))

        expect:
        result.resolveType(type) == new Tuple3<>(group, x11Type, resolved)

        where:
        type       || group       | x11Type     | resolved
        'BYTE'     || 'xproto'    | 'primative' | 'BYTE'
        'PIXMAP'   || 'xproto'    | 'primative' | 'CARD32'
        'FLOAT32'  || 'xproto'    | 'primative' | 'float'
        'FLOAT64'  || 'xproto'    | 'primative' | 'double'
        'DRAWABLE' || 'xproto'    | 'primative' | 'CARD32'
        'POINT'    || 'xproto'    | 'struct'    | 'POINT'
        'void'     || 'xproto'    | 'primative' | 'void'
        'Generic'  || 'glx'       | 'error'    | 'Generic'
    }
}
