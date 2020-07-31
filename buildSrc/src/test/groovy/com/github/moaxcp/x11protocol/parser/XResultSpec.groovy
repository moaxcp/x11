package com.github.moaxcp.x11protocol.parser

import groovy.util.slurpersupport.GPathResult
import groovy.xml.MarkupBuilder
import spock.lang.Specification
import groovy.util.slurpersupport.Node

class XResultSpec extends Specification {
    StringWriter writer = new StringWriter()
    MarkupBuilder xmlBuilder = new MarkupBuilder(writer)
    XResult result = new XResult(header:'xproto')

    GPathResult getGPathResult() {
        new XmlSlurper().parseText(writer.toString())
    }

    def 'Add an xidtype'() {
        given:
        xmlBuilder.xcb() {xidtype(name:'XID')}

        when:
        result.addXidtype((Node) getGPathResult().childNodes().next())

        then:
        result.xidTypes == ['XID':new XType(result:result, type:'primative', name:'CARD32')]
    }

    def 'Add an xidunion'() {
        given:
        xmlBuilder.xcb() {xidunion(name:'XID')}

        when:
        result.addXidunion((Node) getGPathResult().childNodes().next())

        then:
        result.xidUnion == ['XID':new XType(result:result, type:'primative', name:'CARD32')]
    }
}
