package com.github.moaxcp.x11protocol.generator

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec

class ProtocolGenerator {
    File inputXml
    File outputSrc
    String basePackage

    void generate() {
        JavaResult javaResult = JavaParser.parse(basePackage, X11Parser.parseX11(inputXml))
        createClasses(javaResult)
    }

    void createClasses(JavaResult result) {
        result.with {
            [structs, unions, enums, errors, events, eventStructs, requests, replies].each {
                it.each { Map.Entry<String, TypeSpec.Builder> entry ->
                    def javaFile = JavaFile.builder(result.packageName, entry.value.build()).build()
                    javaFile.writeTo(outputSrc)

                }
            }
        }
    }
}
