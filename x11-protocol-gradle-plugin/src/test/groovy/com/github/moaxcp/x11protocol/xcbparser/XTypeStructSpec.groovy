package com.github.moaxcp.x11protocol.xcbparser

import groovy.util.slurpersupport.GPathResult
import groovy.xml.MarkupBuilder
import spock.lang.Specification
import groovy.util.slurpersupport.Node

class XTypeStructSpec extends Specification {
    StringWriter writer = new StringWriter()
    MarkupBuilder xmlBuilder = new MarkupBuilder(writer)
    XResult result = new XResult(basePackage: 'com.github.moaxcp.x11client.protocol', header:'xproto')

    GPathResult getGPathResult() {
        new XmlSlurper().parseText(writer.toString())
    }

    Node getFirstChild() {
        (Node) getGPathResult().childNodes().next()
    }

    void addNodes() {
        getGPathResult().childNodes().each {
            result.addNode(it)
        }
    }

    def 'struct from node with normal fields'() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            struct(name:'POINT') {
                field(type:'INT16', name:'x')
                field(type:'INT16', name:'y')
            }
        }

        when:
        result.addStruct(getFirstChild())

        then:
        result.structs.POINT.name == 'POINT'
        result.structs.POINT.protocol[0].name == 'x'
        result.structs.POINT.protocol[0].type == 'INT16'
        result.structs.POINT.protocol[1].name == 'y'
        result.structs.POINT.protocol[1].type == 'INT16'
    }

    def 'struct from node with normal fields and pad'() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            struct(name:'FORMAT') {
                field(type:'CARD8', name:'depth')
                field(type:'CARD8', name:'bits_per_pixel')
                field(type:'CARD8', name:'scanline_pad')
                pad(bytes:5)
            }
        }

        when:
        result.addStruct(getFirstChild())

        then:
        result.structs.FORMAT.name == 'FORMAT'
        result.structs.FORMAT.protocol[0].name == 'depth'
        result.structs.FORMAT.protocol[0].type == 'CARD8'
        result.structs.FORMAT.protocol[1].name == 'bits_per_pixel'
        result.structs.FORMAT.protocol[1].type == 'CARD8'
        result.structs.FORMAT.protocol[2].name == 'scanline_pad'
        result.structs.FORMAT.protocol[2].type == 'CARD8'

        result.structs.FORMAT.protocol[3].bytes == 5
    }
}
