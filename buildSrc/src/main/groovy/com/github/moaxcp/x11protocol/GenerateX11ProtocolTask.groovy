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
    final DirectoryProperty output = project.objects.directoryProperty()

    @TaskAction
    def writeSource() {
        File out = project.file(output + "Out.java")
        out.write("hello")
    }
}
