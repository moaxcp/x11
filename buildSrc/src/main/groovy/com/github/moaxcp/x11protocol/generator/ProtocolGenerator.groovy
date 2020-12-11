package com.github.moaxcp.x11protocol.generator

import com.github.moaxcp.x11protocol.parser.XParser
import com.github.moaxcp.x11protocol.parser.XResult
import com.github.moaxcp.x11protocol.parser.XTypeReply
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
            XTypeReply reply = it.reply
            if(reply) {
                JavaFile replyFile = JavaFile.builder(result.javaPackage, reply.javaType.typeSpec).build()
                replyFile.writeTo(outputSrc)
            }
        }

        JavaFile pluginFile = JavaFile.builder(result.basePackage, result.getXPlugin()).build()
        pluginFile.writeTo(outputSrc)

        String pluginClass = result.pluginClassName.canonicalName()
        File services = new File(outputSrc, "META-INF/services/${basePackage}.XProtocolPlugin")
        services.parentFile.mkdirs()

        services.append("$pluginClass\n")
    }
}
