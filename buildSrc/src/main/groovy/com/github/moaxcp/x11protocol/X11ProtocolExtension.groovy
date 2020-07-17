package com.github.moaxcp.x11protocol

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory

import javax.inject.Inject

class X11ProtocolExtension {
    final DirectoryProperty xcbXmls
    final DirectoryProperty outputSrc

    @Inject
    X11ProtocolExtension(ObjectFactory objects) {
        xcbXmls = objects.directoryProperty()
        outputSrc = objects.directoryProperty()
    }
}
