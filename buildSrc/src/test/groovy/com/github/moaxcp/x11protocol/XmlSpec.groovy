package com.github.moaxcp.x11protocol

import com.github.moaxcp.x11protocol.xcbparser.XParser
import com.github.moaxcp.x11protocol.xcbparser.XResult
import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.Node
import groovy.xml.MarkupBuilder
import spock.lang.Specification

abstract class XmlSpec extends Specification {
    StringWriter writer = new StringWriter()
    MarkupBuilder xmlBuilder = new MarkupBuilder(writer)
    XResult result = new XResult(basePackage: 'com.github.moaxcp.x11client.protocol', header:'xproto')

    GPathResult getGPathResult() {
        new XmlSlurper().parseText(writer.toString())
    }

    void parseXml() {
        XParser parser = new XParser(xml: getGPathResult(), result: result)
        parser.parseXml()
    }

    void parseHeaderAndXml() {
        XParser parser = new XParser(xml: getGPathResult(), result: result)
        parser.parseHeader()
        parser.parseXml()
    }

    Node getFirstChild() {
        (Node) getGPathResult().childNodes().next()
    }

    Node getFirstNode() {
        getGPathResult().nodeIterator().next()
    }

    void addFirstNode() {
        result.addNode(firstNode)
    }

    void addChildNodes() {
        getFirstNode().childNodes().each {
            result.addNode(it)
        }
    }
}
