package com.github.moaxcp.x11protocol

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class X11ProtocolPluginSpec extends Specification {

    def 'x11protocol plugin adds generateX11Protocol task'() {
        given:
        Project project = ProjectBuilder.builder().build()

        when:
        project.getPluginManager().apply('com.github.moaxcp.x11protocol')

        then:
        project.getTasks().getByName("generateX11Protocol") instanceof GenerateX11ProtocolTask
    }
}
