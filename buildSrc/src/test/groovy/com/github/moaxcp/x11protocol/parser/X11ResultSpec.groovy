package com.github.moaxcp.x11protocol.parser

import spock.lang.Specification

class X11ResultSpec extends Specification {
    def 'glx resolveType'() {
        given:
        X11Result result = X11Parser.parse(new File('../src/main/xcbXmls/glx.xml'))

        expect:
        result.resolveType(type) == new Tuple2<>(group, resolved)

        where:
        type       || group       | resolved
        'BYTE'     || 'primative' | 'BYTE'
        'PIXMAP'   || 'primative' | 'CARD32'
        'FLOAT32'  || 'primative' | 'float'
        'FLOAT64'  || 'primative' | 'double'
        'DRAWABLE' || 'primative' | 'CARD32'
        'POINT'    || 'xproto'    | 'POINT'
        'Generic'  || 'glx'       | 'Generic'
    }
}
