package com.github.moaxcp.x11protocol.parser.expression

import com.github.moaxcp.x11protocol.XmlSpec

class SumOfExpressionSpec extends XmlSpec {
    def 'ref only'() {
        given:



        expect:
        expression.expression.toString() == ''
    }
}
