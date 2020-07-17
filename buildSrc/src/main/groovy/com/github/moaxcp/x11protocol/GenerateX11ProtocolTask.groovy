package com.github.moaxcp.x11protocol

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
        xcbXmls.get().asFileTree.each {
            ProtocolGenerator gen = new ProtocolGenerator(inputStream: it.newInputStream(), outputSrc: outputSrc.get().asFile, basePackage: 'com.github.moaxcp.x11client.protocol')
            gen.generate()
        }
    }
}
