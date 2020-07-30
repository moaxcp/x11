package com.github.moaxcp.x11protocol.generator

import groovy.xml.MarkupBuilder
import spock.lang.Specification

class X11ResultSpec extends Specification {
    StringWriter writer = new StringWriter()
    MarkupBuilder xmlBuilder = new MarkupBuilder(writer)

    def 'glx resolveType'() {
        given:
        X11Result result = X11Parser.parseX11(new File('../src/main/xcbXmls/glx.xml'))

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

    def 'resolve primatives'() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            xidtype(name:'WINDOW')
            xidtype(name:'FONT')
            xidtype(name:'PIXMAP')
            xidtype(name:'DRAWABLE')
            typedef(oldname:'CARD32', newname:'TIMESTAMP')
            typedef(oldname:'float', newname:'FLOAT32')
            typedef(oldname:'double', newname:'FLOAT64')
            struct(name:'POINT') {
                field(type:'INT16', name:'x')
            }
        }

        X11Result result = X11Parser.parseX11(writer.toString())

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

    }
}
