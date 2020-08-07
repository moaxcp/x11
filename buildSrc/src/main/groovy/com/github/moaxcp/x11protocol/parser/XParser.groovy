package com.github.moaxcp.x11protocol.parser


import groovy.transform.Memoized
import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.Node

class XParser {
    XResult result
    GPathResult xml

    static XResult parse(String basePackage, File file) {
        return new XParser().parseFile(basePackage, file)
    }

    static XResult parse(String basePackage, String text) {
        new XParser().parseText(basePackage, text)
    }

    XResult parseFile(String basePackage, File file) {
        result = new XResult(file:file, basePackage: basePackage)
        xml = new XmlSlurper().parse(file)
        parseXml()
        return result
    }

    XResult parseText(String basePackage, String text) {
        result = new XResult(basePackage: basePackage)
        xml = new XmlSlurper().parseText(text)
        parseXml()
        return result

    }

    void parseXml() {
        parseHeader()
        xml.childNodes().each {
            if(it instanceof Node) {
                if(it.name() == 'import') {
                    parseImport(it)
                } else {
                    result.addNode(it)
                }
            }
        }
    }

    void parseHeader() {
        result.header = xml.@header
        result.extensionXName = xml.@"extension-xname"
        result.extensionName = xml.@"extension-name"
        result.extensionMultiword = xml.@"extenion-multiword"
        result.majorVersion = ((String) xml.@"major-version") ? Integer.valueOf((String) xml.@"major-version") : 0
        result.minorVersion = ((String) xml.@"major-version") ? Integer.valueOf((String) xml.@"minor-version") : 0
    }

    private void parseImport(Node node) {
        XResult importResult = cachedParseImport(node)
        result.imports.put(importResult.header, importResult)
    }

    @Memoized
    private XResult cachedParseImport(Node node) {
        String name = node.text()
        File importFile = new File(result.file.parent + File.separator + name + '.xml')
        return parse(importFile)
    }
}
