package com.github.moaxcp.x11protocol.parser

import spock.lang.Specification

class XPadSpec extends Specification {
    def 'XPad read expression'() {
        given:
        XPad xpad = new XPad(bytes:4)

        expect:
        xpad.getReadCode().toString() == 'in.readPad(4)'
    }

    def 'XPad write expression'() {
        given:
        XPad xpad = new XPad(bytes:4)

        expect:
        xpad.getWriteCode().toString() == 'out.writePad(4)'
    }
}
