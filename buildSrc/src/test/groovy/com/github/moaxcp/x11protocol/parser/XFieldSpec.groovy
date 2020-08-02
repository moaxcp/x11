package com.github.moaxcp.x11protocol.parser

import spock.lang.Specification

class XFieldSpec extends Specification {
    def 'normal field read code'() {
        given:
        XResult result = new XResult()
        XField field = new XField(result:result, type:'VISUALID', name:'visual_id')

        expect:
        field.readCode == 'visualId = in.readCard32();'
    }

    def 'normal field write code'() {
        given:
        XResult result = new XResult()
        XField field = new XField(result:result, type:'VISUALID', name:'visual_id')

        expect:
        field.writeCode == 'out.writeCard32(visualId);'
    }
}
