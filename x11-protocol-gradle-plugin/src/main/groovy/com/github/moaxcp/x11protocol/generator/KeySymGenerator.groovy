package com.github.moaxcp.x11protocol.generator


import com.squareup.javapoet.JavaFile

class KeySymGenerator {
    File headerSrc
    File outputSrc
    String basePackage

    void generate() {
        KeySymParser parser = new KeySymParser(basePackage)
        headerSrc.listFiles().collect {file ->
            file.withReader { reader ->
                parser.merge(reader)
            }
        }
        JavaFile javaFile = JavaFile.builder(basePackage, parser.typeSpec).skipJavaLangImports(true).build()
        javaFile.writeTo(outputSrc)
    }
}
