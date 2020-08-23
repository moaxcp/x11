package com.github.moaxcp.x11protocol.parser


import static com.github.moaxcp.x11protocol.generator.Conventions.getEventJavaName
import static com.github.moaxcp.x11protocol.generator.Conventions.getEventTypeName

class JavaEvent extends JavaBaseObject {

    static JavaEvent javaEvent(XTypeEvent event) {
        List<JavaUnit> protocol = event.toJavaProtocol()

        String simpleName = getEventJavaName(event.name)

        return new JavaEvent(
            superType: event.superType,
            basePackage: event.basePackage,
            javaPackage: event.javaPackage,
            simpleName:simpleName,
            className:getEventTypeName(event.javaPackage, event.name),
            protocol:protocol
        )
    }
}
