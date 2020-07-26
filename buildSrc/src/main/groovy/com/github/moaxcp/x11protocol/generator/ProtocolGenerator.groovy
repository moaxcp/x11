package com.github.moaxcp.x11protocol.generator


import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec

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
