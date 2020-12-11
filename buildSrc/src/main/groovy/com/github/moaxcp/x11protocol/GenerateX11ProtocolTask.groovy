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
        xcbXmls.get().asFileTree
            .findAll {
                it.name.endsWith('xproto.xml') || it.name.endsWith('bigreq.xml') || it.name.endsWith('composite.xml') || it.name.endsWith('damage.xml') || it.name.endsWith('dpms.xml') || it.name.endsWith('dri2.xml') || it.name.endsWith('ge.xml') || it.name.endsWith('record.xml') || it.name.endsWith('res.xml') || it.name.endsWith('screensaver.xml') || it.name.endsWith('shape.xml') || it.name.endsWith('xc_misc.xml') || it.name.endsWith('xevie.xml') || it.name.endsWith('xf86dri.xml') || it.name.endsWith('xf86vidmode.xml') || it.name.endsWith('xfixes.xml') || it.name.endsWith('xinerama.xml') || it.name.endsWith('xselinux.xml') || it.name.endsWith('xtest.xml')
            }
            .each {
                ProtocolGenerator gen = new ProtocolGenerator(inputXml: it, outputSrc: outputSrc.get().asFile, basePackage: 'com.github.moaxcp.x11client.protocol')
                gen.generate()
            }
    }
}
