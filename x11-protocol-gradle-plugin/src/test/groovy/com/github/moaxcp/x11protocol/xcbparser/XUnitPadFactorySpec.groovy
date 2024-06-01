package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec
import com.github.moaxcp.x11protocol.xcbparser.JavaClass
import com.github.moaxcp.x11protocol.xcbparser.JavaPad
import com.github.moaxcp.x11protocol.xcbparser.JavaPadAlign
import com.github.moaxcp.x11protocol.xcbparser.JavaType
import com.github.moaxcp.x11protocol.xcbparser.XUnit
import com.github.moaxcp.x11protocol.xcbparser.XUnitPad
import com.github.moaxcp.x11protocol.xcbparser.XUnitPadAlign

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
        JavaType javaType = Mock(JavaClass) {
            it.getXUnitSubtype() >> Optional.empty()
        }

        when:
        JavaPad javaPad = pad.getJavaUnit(javaType)[0]

        then:
        javaPad.bytes == 4
    }

    def 'convert pad align'() {
        given:
        xmlBuilder.pad(align:4)
        XUnitPadAlign pad = xUnitPad(getFirstNode())
        JavaClass javaClass = Mock(JavaClass)

        when:
        JavaPadAlign javaPad = pad.getJavaUnit(javaClass)[0]

        then:
        javaPad.align == 4
    }
}
