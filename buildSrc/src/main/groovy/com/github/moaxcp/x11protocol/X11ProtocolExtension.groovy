package com.github.moaxcp.x11protocol

import javax.inject.Inject
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.SetProperty

class X11ProtocolExtension {
    final DirectoryProperty xcbXmls
    final SetProperty<String> exclude
    final DirectoryProperty outputSrc
    final DirectoryProperty outputResources
    final RegularFileProperty keysymHeader

    @Inject
    X11ProtocolExtension(ObjectFactory objects) {
        xcbXmls = objects.directoryProperty()
        exclude = objects.setProperty(String.class)
        outputSrc = objects.directoryProperty()
        outputResources = objects.directoryProperty()
        keysymHeader = objects.fileProperty()
    }
}
