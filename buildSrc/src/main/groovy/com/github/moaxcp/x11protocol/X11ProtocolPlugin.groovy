package com.github.moaxcp.x11protocol

import org.gradle.api.Plugin
import org.gradle.api.Project

class X11ProtocolPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create('x11Protocol', X11ProtocolExtension)
        def task = project.task(type: GenerateX11ProtocolTask, 'generateX11Protocol')
        task.xcbXmls = project.x11Protocol.xcbXmls
        task.outputSrc = project.x11Protocol.outputSrc
    }
}
