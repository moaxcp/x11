package com.github.moaxcp.x11protocol

import org.gradle.api.Plugin
import org.gradle.api.Project

class X11ProtocolPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.task(type: GenerateX11ProtocolTask, 'generateX11Protocol')
    }
}
