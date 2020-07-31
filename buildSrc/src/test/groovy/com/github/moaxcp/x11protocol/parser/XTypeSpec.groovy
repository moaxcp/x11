package com.github.moaxcp.x11protocol.parser

import spock.lang.Specification

class XTypeSpec extends Specification {
    XResult result = new XResult()

    def setup() {
        result.header = 'xproto'
    }
    def 'check constructor'() {
        given:
        XType type = new XType(result, 'primative', 'CARD32')

        expect:
        type.name == 'CARD32'
        type.type == 'primative'
        type.group == 'xproto'
    }
}
