package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock
import spock.lang.Specification

class JavaPadUnitSpec extends Specification {
    def 'XPad read expression'() {
        given:
        JavaPad xpad = new JavaPad(bytes:4)

        expect:
        xpad.getDeclareAndReadCode().toString() == 'in.readPad(4);\n'
    }

    def 'XPad write expression'() {
        given:
        JavaPad xpad = new JavaPad(bytes:4)

        when:
        CodeBlock.Builder builder = CodeBlock.builder()
        xpad.addWriteCode(builder)

        then:
        builder.build().toString() == 'out.writePad(4);\n'
    }
}
