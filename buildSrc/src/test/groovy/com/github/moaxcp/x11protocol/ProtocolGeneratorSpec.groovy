package com.github.moaxcp.x11protocol

import spock.lang.Specification

class ProtocolGeneratorSpec extends Specification {
    def 'parse xproto has correct package'() {
        given:
        File file = new File('../src/main/xcbXmls/xproto.xml')
        ProtocolGenerator gen = new ProtocolGenerator(inputStream:file.newInputStream(), outputSrc:new File('build/generated/sources/x11generator'), basePackage: 'com.github.moaxcp.x11client.protocol')

        when:
        gen.generate()

        then:
        gen.fullPackage == 'com.github.moaxcp.x11client.protocol.xproto'
    }
}
