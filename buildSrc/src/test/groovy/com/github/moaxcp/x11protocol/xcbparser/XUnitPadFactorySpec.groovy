package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec

import static com.github.moaxcp.x11protocol.xcbparser.XUnitPadFactory.xUnitPad

class XUnitPadFactorySpec extends XmlSpec {
    def 'parse pad'() {
        given:
        xmlBuilder.pad(bytes:4)

        when:
        XUnit pad = xUnitPad(getFirstNode())

        then:
        pad instanceof XUnitPad
        pad.bytes == 4
    }

    def 'parse pad align'() {
        given:
        xmlBuilder.pad(align:4)

        when:
        XUnit pad = xUnitPad(getFirstNode())

        then:
        pad instanceof XUnitPadAlign
        pad.align == 4
    }
    
    def 'convert pad'() {
        given:
        xmlBuilder.pad(bytes:4)
        XUnitPad pad = (XUnitPad) xUnitPad(getFirstNode())
        JavaType javaType = Mock(JavaType)

        when:
        JavaPad javaPad = pad.getJavaUnit(javaType)[0]

        then:
        javaPad.bytes == 4
    }

    def 'convert pad align'() {
        given:
        xmlBuilder.pad(align:4)
        XUnitPadAlign pad = xUnitPad(getFirstNode())
        JavaType javaType = Mock(JavaType)

        when:
        JavaPadAlign javaPad = pad.getJavaUnit(javaType)[0]

        then:
        javaPad.align == 4
    }
}
