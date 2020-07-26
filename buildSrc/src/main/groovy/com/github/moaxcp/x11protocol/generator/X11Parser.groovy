package com.github.moaxcp.x11protocol.generator

import groovy.transform.Memoized
import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.Node

class X11Parser {
    private X11Result result
    private GPathResult xml


    static X11Result parse(File file) {
        return new X11Parser().parseFile(file)
    }

    private X11Parser() {

    }

    private X11Result parseFile(File file) {
        result = new X11Result(file:file)
        xml = new XmlSlurper().parse(file)
        parseHeader()
        xml.childNodes().each { node ->
            if(node instanceof Node) {
                String name= node.name().capitalize()
                "parse$name"(node)
            } else {
                //text
            }
        }
        return result
    }

    private void parseHeader() {
        result.header = xml.@header
        result.extensionXName = xml.@"extension-xname"
        result.extensionName = xml.@"extension-name"
        result.extensionMultiword = xml.@"extenion-multiword"
        result.majorVersion = ((String) xml.@"major-version") ? Integer.valueOf((String) xml.@"major-version") : 0
        result.minorVersion = ((String) xml.@"major-version") ? Integer.valueOf((String) xml.@"minor-version") : 0
    }

    private void parseImport(Node node) {
        X11Result importResult = cachedParseImport(node)
        result.imports.put(importResult.header, importResult)
    }

    @Memoized
    private X11Result cachedParseImport(Node node) {
        String name = node.text()
        File importFile = new File(result.file.parent + File.separator + name + '.xml')
        return parse(importFile)
    }

    private void parseXidtype(Node node) {
        result.xidTypes += node.attributes().get('name')
    }

    private void parseXidunion(Node node) {
        result.xidUnions += node.attributes().get('name')
    }

    private void parseTypedef(Node node) {
        result.typedefs.put((String) node.attributes().get('newname'), (String) node.attributes().get('oldname'))
    }

    private void parseStruct(Node node) {
        result.structs.put((String) node.attributes().get('name'), node)
    }

    private void parseUnion(Node node) {
        result.unions.put((String) node.attributes().get('name'), node)
    }

    private void parseEnum(Node node) {
        result.enums.put((String) node.attributes().get('name'), node)
    }

    private void parseError(Node node) {
        result.errors.put((String) node.attributes().get('name'), node)
    }

    private void parseErrorcopy(Node node) {
        result.errorCopies.put((String) node.attributes().get('name'), node)
    }

    private void parseEvent(Node node) {
        result.events.put((String) node.attributes().get('name'), node)
    }

    private void parseEventcopy(Node node) {
        result.eventCopies.put((String) node.attributes().get('name'), node)
    }

    private void parseEventstruct(Node node) {
        result.eventStructs.put((String) node.attributes().get('name'), node)
    }

    private void parseRequest(Node node) {
        result.requests.put((String) node.attributes().get('name'), node)
    }
}
