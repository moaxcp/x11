package com.github.moaxcp.x11protocol.xcbparser

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
        result.xidTypes == ['XID'] as Set
    }

    def 'Add an xidunion'() {
        given:
        xmlBuilder.xcb() {xidunion(name:'XID')}

        when:
        result.addXidunion((Node) getGPathResult().childNodes().next())

        then:
        result.xidUnions == ['XID'] as Set
    }

    def 'resolve failed'() {
        when:
        result.resolveXType('type')

        then:
        thrown(IllegalArgumentException)
    }

    def 'resolve an xid'() {
        given:
        xmlBuilder.xcb(header:'xproto') {xidunion(name:'XID')}
        result.addXidtype((Node) getGPathResult().childNodes().next())

        when:
        XType type = result.resolveXType('XID')

        then:
        type.name == 'CARD32'
    }

    def 'resolve an xid from import'() {
        given:
        xmlBuilder.xcb() {xidunion(name:'XID')}
        XResult imported = new XResult(header:'imported')
        imported.addXidtype((Node) getGPathResult().childNodes().next())
        result.addImport(imported)

        when:
        XTypePrimative type = result.resolveXType('XID')

        then:
        type.name == 'CARD32'
    }

    def 'resolve an xid from specific import'() {
        given:
        xmlBuilder.xcb() {xidunion(name:'XID')}
        XResult imported = new XResult(header:'imported')
        imported.addXidtype((Node) getGPathResult().childNodes().next())
        result.addImport(imported)

        when:
        XTypePrimative type = result.resolveXType('imported:XID')

        then:
        type.name == 'CARD32'
    }

    def 'resolve an xidunion'() {
        given:
        xmlBuilder.xcb() {xidunion(name:'XID')}
        result.addXidunion((Node) getGPathResult().childNodes().next())

        when:
        XTypePrimative type = result.resolveXType('XID')

        then:
        type.name == 'CARD32'
    }

    def 'resolve BOOL from typedef'() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            typedef(oldname:'BOOL', newname:'TRUTH')
        }
        result.addTypeDef((Node) getGPathResult().childNodes().next())

        when:
        XTypePrimative type = result.resolveXType('TRUTH')

        then:
        type.name == 'BOOL'
    }

    def 'resolve CARD32 from typedef'() {
        given:
        xmlBuilder.xcb() {
            typedef(oldname:'CARD32', newname:'VISUALID')
        }
        result.addTypeDef((Node) getGPathResult().childNodes().next())

        when:
        XTypePrimative type = result.resolveXType('VISUALID')

        then:
        type.name == 'CARD32'
    }
}
