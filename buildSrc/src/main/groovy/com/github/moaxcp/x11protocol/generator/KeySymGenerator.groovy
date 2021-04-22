package com.github.moaxcp.x11protocol.generator

import com.github.moaxcp.x11protocol.KeySymParser
import com.squareup.javapoet.JavaFile

class KeySymGenerator {
    File header
    File outputSrc
    String basePackage

    void generate() {
        header.withReader {
            KeySymParser parser = new KeySymParser(basePackage: basePackage, input: it)
            JavaFile javaFile = JavaFile.builder(basePackage, parser.typeSpec).skipJavaLangImports(true).build()
            javaFile.writeTo(outputSrc)
        }
    }
}
