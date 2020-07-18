package com.github.moaxcp.x11protocol

import com.squareup.javapoet.JavaFile

class ProtocolGenerator {
    File inputXml
    File outputSrc
    String basePackage

    void generate() {
        ProtocolParser parser = new ProtocolParser(basePackage:basePackage, file:inputXml)
        createClasses(parser.parse())
    }

    void createClasses(ParseResult result) {
        result.javaTypes.values()*.each {
            def javaFile = JavaFile.builder(result.packageName, it).build()
            javaFile.writeTo(outputSrc)
        }
    }
}
