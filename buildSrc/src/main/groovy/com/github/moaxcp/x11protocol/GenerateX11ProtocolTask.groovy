package com.github.moaxcp.x11protocol


import com.github.moaxcp.x11protocol.generator.ProtocolGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

class GenerateX11ProtocolTask extends DefaultTask {
    @InputDirectory
    final DirectoryProperty xcbXmls = project.objects.directoryProperty()

    @OutputDirectory
    final DirectoryProperty outputSrc = project.objects.directoryProperty()

    @TaskAction
    def writeSource() {
        xcbXmls.get().asFileTree.findAll { it.name.endsWith('xproto.xml') }.each {
            ProtocolGenerator gen = new ProtocolGenerator(inputXml: it, outputSrc: outputSrc.get().asFile, basePackage: 'com.github.moaxcp.x11client.protocol')
            gen.generate()
        }
    }
}
