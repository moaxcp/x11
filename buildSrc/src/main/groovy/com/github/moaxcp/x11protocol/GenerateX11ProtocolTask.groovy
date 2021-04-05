package com.github.moaxcp.x11protocol

import com.github.moaxcp.x11protocol.generator.KeySymGenerator
import com.github.moaxcp.x11protocol.generator.ProtocolGenerator
import com.google.common.io.Files
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.*

class GenerateX11ProtocolTask extends DefaultTask {
    @InputDirectory
    final DirectoryProperty xcbXmls = project.objects.directoryProperty()

    @Input
    final SetProperty<String> exclude = project.objects.setProperty(String.class)

    @OutputDirectory
    final DirectoryProperty outputSrc = project.objects.directoryProperty()

    @OutputDirectory
    final DirectoryProperty outputResources = project.objects.directoryProperty()

    @InputFile
    final RegularFileProperty keysymHeader = project.objects.fileProperty()

    @TaskAction
    def writeSource() {
        Set<String> excludeNames = exclude.get()
        xcbXmls.get().asFileTree
            .findAll {
                it.name.endsWith('xml') && !excludeNames.contains(Files.getNameWithoutExtension(it.toString()))
            }
            .each {
                try {
                    ProtocolGenerator gen = new ProtocolGenerator(inputXml: it, outputSrc: outputSrc.get().asFile, outputResources: outputResources.get().asFile, basePackage: 'com.github.moaxcp.x11client.protocol')
                    gen.generate()
                } catch(Exception e) {
                    logger.error("error with file $it.name", e)
                    throw e
                }
            }

        new KeySymGenerator(header: keysymHeader.get().asFile, outputSrc: outputSrc.get().asFile, basePackage: 'com.github.moaxcp.x11client.protocol').generate()
    }
}
