package com.github.moaxcp.x11protocol.generator


import com.squareup.javapoet.JavaFile

class KeySymGenerator {
    File headerSrc
    File outputSrc
    String corePackage
    String keysymPackage

    void generate() {
        KeySymParser parser = new KeySymParser(corePackage, keysymPackage)
        headerSrc.listFiles().collect {file ->
            file.withReader { reader ->
                parser.merge(reader)
            }
        }
        JavaFile javaFile = JavaFile.builder(keysymPackage, parser.typeSpec).skipJavaLangImports(true).build()
        javaFile.writeTo(outputSrc)
    }
}
