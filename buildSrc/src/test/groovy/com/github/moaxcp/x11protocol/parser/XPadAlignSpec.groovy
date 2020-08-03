package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.ValueExpression
import spock.lang.Specification

class XPadAlignSpec extends Specification {
    def 'XPadAlign read expression'() {
        given:
        XPadAlign align = new XPadAlign(align:5,
            list: new XListField(lengthExpression: new ValueExpression(value:5)))

        expect:
        align.readCode.toString() == 'in.readPadAlign(5, 5)'
    }

    def 'XPadAlign write expression'() {
        given:
        XPadAlign align = new XPadAlign(align:5,
            list: new XListField(lengthExpression: new ValueExpression(value:5)))

        expect:
        align.writeCode.toString() == 'out.writePadAlign(5, 5)'
    }
}
