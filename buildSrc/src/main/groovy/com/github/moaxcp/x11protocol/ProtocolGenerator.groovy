package com.github.moaxcp.x11protocol

class ProtocolGenerator {
    InputStream inputStream
    File outputSrc
    String basePackage
    String fullPackage

    Map<String, String> types

    void generate() {
        def root = new XmlSlurper().parse(inputStream)
        fullPackage = basePackage + ".${root.@header}"
    }
}
