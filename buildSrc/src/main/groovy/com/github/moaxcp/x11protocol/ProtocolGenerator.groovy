package com.github.moaxcp.x11protocol

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import groovy.util.slurpersupport.GPathResult

class ProtocolGenerator {
    InputStream inputStream
    File outputSrc
    String basePackage
    String fullPackage

    Map<String, String> types

    void generate() {
        def root = new XmlSlurper().parse(inputStream)
        fullPackage = basePackage + ".${root.@header}"
        def format = root.breadthFirst().find { it.name() == 'struct' && it.@name == 'FORMAT' }
        if(format) {
            createStruct(format)
        }
    }

    void createStruct(GPathResult struct) {
        String name = struct.@name
        StringBuilder className = new StringBuilder()
        if(name.capitalize() == name) {
            className.append(name.charAt(0))
            className.append(name.substring(1).toLowerCase())
        } else {
            className.append(name)
        }
        def type = TypeSpec.classBuilder(className.toString())

        struct.childNodes()

        def javaFile = JavaFile.builder(fullPackage, type.build())
                .build()

        javaFile.writeTo(outputSrc)
    }
}
