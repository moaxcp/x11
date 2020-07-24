package com.github.moaxcp.x11protocol

import com.github.moaxcp.x11protocol.parser.JavaParser
import com.github.moaxcp.x11protocol.parser.JavaResult
import com.github.moaxcp.x11protocol.parser.X11Parser
import com.squareup.javapoet.JavaFile

class ProtocolGenerator {
    File inputXml
    File outputSrc
    String basePackage

    void generate() {
        JavaResult javaResult = JavaParser.parse(basePackage, X11Parser.parse(inputXml))
        createClasses(javaResult)
    }

    void createClasses(JavaResult result) {
        result.javaTypes.values().each {
            def javaFile = JavaFile.builder(result.packageName, it).build()
            javaFile.writeTo(outputSrc)
        }
    }
}
