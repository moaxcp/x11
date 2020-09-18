package com.github.moaxcp.x11protocol.generator

import com.github.moaxcp.x11protocol.parser.XParser
import com.github.moaxcp.x11protocol.parser.XResult
import com.squareup.javapoet.JavaFile

class ProtocolGenerator {
    File inputXml
    File outputSrc
    String basePackage

    void generate() {
        XResult result = XParser.parse(basePackage, inputXml)
        result.enums.values().each {
            JavaFile javaFile = JavaFile.builder(result.javaPackage, it.javaType.typeSpec).build()
            javaFile.writeTo(outputSrc)
        }
        result.structs.values().each {
            JavaFile javaFile = JavaFile.builder(result.javaPackage, it.javaType.typeSpec).build()
            javaFile.writeTo(outputSrc)
        }
        result.unions.values().each {
            JavaFile javaFile = JavaFile.builder(result.javaPackage, it.javaType.typeSpec).build()
            javaFile.writeTo(outputSrc)
        }
        result.events.values().each {
            JavaFile javaFile = JavaFile.builder(result.javaPackage, it.javaType.typeSpec).build()
            javaFile.writeTo(outputSrc)
        }
        result.errors.values().each {
            JavaFile javaFile = JavaFile.builder(result.javaPackage, it.javaType.typeSpec).build()
            javaFile.writeTo(outputSrc)
        }
        result.requests.values().each {
            JavaFile javaFile = JavaFile.builder(result.javaPackage, it.javaType.typeSpec).build()
            javaFile.writeTo(outputSrc)
        }
    }
}
