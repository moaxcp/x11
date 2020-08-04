package com.github.moaxcp.x11protocol.parser


import spock.lang.Specification

class XFieldSpec extends Specification {
    def 'CARD32 field'() {
        given:
        XResult result = new XResult()
        XField field = new XField(result:result, type:'CARD32', name:'red_mask')

        expect:
        field.resolvedType == new XType(result:result, type:'primative', name:'CARD32')
        field.readCode.toString() == 'int redMask = in.readCard32()'
        field.writeCode.toString() == 'out.writeCard32(redMask)'
        field.member.toString() == 'private int redMask;\n'
        field.readOnly == false
    }

    def 'xid field read code'() {
        given:
        XResult result = new XResult()
        result.xidTypes['VISUALID'] = new XType(result:result, type:'xid', name:'VISUALID')
        XField field = new XField(result:result, type:'VISUALID', name:'visual_id')

        expect:
        field.readCode.toString() == 'int visualId = in.readCard32()'
    }

    def 'xid field write code'() {
        given:
        XResult result = new XResult()
        result.xidTypes['VISUALID'] = new XType(result:result, type:'xid', name:'VISUALID')
        XField field = new XField(result:result, type:'VISUALID', name:'visual_id')

        expect:
        field.writeCode.toString() == 'out.writeCard32(visualId)'
    }
}
