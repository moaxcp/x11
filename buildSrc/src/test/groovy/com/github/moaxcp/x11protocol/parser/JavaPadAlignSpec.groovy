package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.ValueExpression
import spock.lang.Specification

class JavaPadAlignSpec extends Specification {
    def 'XPadAlign read expression 4'() {
        given:
        JavaPadAlign align = new JavaPadAlign(align:4,
            list: new JavaPrimativeListProperty(lengthExpression: new ValueExpression(value:5)))

        expect:
        align.readCode.toString() == 'in.readPadAlign(5)'
    }

    def 'XPadAlign read expression'() {
        given:
        JavaPadAlign align = new JavaPadAlign(align:5,
            list: new JavaPrimativeListProperty(lengthExpression: new ValueExpression(value:5)))

        expect:
        align.readCode.toString() == 'in.readPadAlign(5, 5)'
    }

    def 'XPadAlign write expression 4'() {
        given:
        JavaPadAlign align = new JavaPadAlign(align:4,
            list: new JavaPrimativeListProperty(lengthExpression: new ValueExpression(value:5)))

        expect:
        align.writeCode.toString() == 'out.writePadAlign(5)'
    }

    def 'XPadAlign write expression'() {
        given:
        JavaPadAlign align = new JavaPadAlign(align:5,
            list: new JavaPrimativeListProperty(lengthExpression: new ValueExpression(value:5)))

        expect:
        align.writeCode.toString() == 'out.writePadAlign(5, 5)'
    }
}
