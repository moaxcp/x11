package com.github.moaxcp.x11protocol.parser

import spock.lang.Specification

class JavaPadUnitSpec extends Specification {
    def 'XPad read expression'() {
        given:
        JavaPad xpad = new JavaPad(bytes:4)

        expect:
        xpad.getReadCode().toString() == 'in.readPad(4);\n'
    }

    def 'XPad write expression'() {
        given:
        JavaPad xpad = new JavaPad(bytes:4)

        expect:
        xpad.getWriteCode().toString() == 'out.writePad(4);\n'
    }
}
