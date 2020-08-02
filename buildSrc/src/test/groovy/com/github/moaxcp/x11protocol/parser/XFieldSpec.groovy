package com.github.moaxcp.x11protocol.parser


import spock.lang.Specification

class XFieldSpec extends Specification {
    def 'CARD32 field'() {
        given:
        XResult result = new XResult()
        XField field = new XField(result:result, type:'CARD32', name:'red_mask')

        expect:
        field.resolvedType == new XType(result:result, type:'primative', name:'CARD32')
        field.readCode.toString() == 'redMask = in.readCard32();\n'
        field.writeCode.toString() == 'out.writeCard32(redMask);\n'
        field.member.toString() == 'private int redMask;\n'
        field.readOnly == false
    }
    def 'read only CARD32 field'() {
        given:
        XResult result = new XResult()
        XField field = new XField(result:result, type:'CARD32', name:'red_mask', readOnly: true)

        expect:
        field.resolvedType == new XType(result:result, type:'primative', name:'CARD32')
        field.readCode.toString() == 'redMask = in.readCard32();\n'
        field.writeCode.toString() == 'out.writeCard32(redMask);\n'
        field.member.toString() == '@lombok.Setter(AccessLevel.NONE)\nprivate int redMask;\n'
        field.readOnly == true
    }

    def 'xid field read code'() {
        given:
        XResult result = new XResult()
        result.xidTypes['VISUALID'] = new XType(result:result, type:'xid', name:'VISUALID')
        XField field = new XField(result:result, type:'VISUALID', name:'visual_id')

        expect:
        field.readCode.toString() == 'visualId = in.readCard32();\n'
    }

    def 'xid field write code'() {
        given:
        XResult result = new XResult()
        result.xidTypes['VISUALID'] = new XType(result:result, type:'xid', name:'VISUALID')
        XField field = new XField(result:result, type:'VISUALID', name:'visual_id')

        expect:
        field.writeCode.toString() == 'out.writeCard32(visualId);\n'
    }
}
