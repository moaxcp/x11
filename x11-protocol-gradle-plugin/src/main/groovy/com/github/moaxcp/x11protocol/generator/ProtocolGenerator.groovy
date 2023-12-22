package com.github.moaxcp.x11protocol.generator

import com.github.moaxcp.x11protocol.xcbparser.*
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec

class ProtocolGenerator {
    File inputXml
    File outputSrc
    File outputResources
    String basePackage

    void generate() {
        XResult result = XParser.parse(basePackage, inputXml)
        writeToFile(result.javaPackage, result.enums.values())
        writeToFile(result.javaPackage, result.structs.values())
        writeToFile(result.javaPackage, result.unions.values())
        writeToFile(result.javaPackage, result.events.values())
        writeToFile(result.javaPackage, result.errors.values())

        result.requests.values().each {
            writeToFile(result.javaPackage, it)
            XTypeReply reply = it.reply
            if(reply) {
                writeToFile(result.javaPackage, reply)
            }
        }

        writeToFile(result.javaPackage, result.getXPlugin())

        String pluginClass = result.pluginClassName.canonicalName()
        File services = new File(outputResources, "META-INF/services/${basePackage}.XProtocolPlugin")
        services.parentFile.mkdirs()

        services.append("$pluginClass\n")
    }

    private void writeToFile(String javaPackage, Collection<XTypeUnit> units) {
        units.each {main ->
            writeToFile(javaPackage, main)
        }
    }

    private void writeToFile(String javaPackage, XTypeUnit unit) {
        if(unit.hasSubTypes()) {
            unit.getSubTypes().each {sub ->
                writeToFile(javaPackage, sub.typeSpec)
            }
        } else {
            writeToFile(javaPackage, unit.javaType.typeSpec)
        }
    }

    private void writeToFile(String javaPackage, TypeSpec typeSpec) {
        JavaFile javaFile = JavaFile.builder(javaPackage, typeSpec).skipJavaLangImports(true).build()
        javaFile.writeTo(outputSrc)
    }
}
