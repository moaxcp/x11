package com.github.moaxcp.x11protocol.parser


import static com.github.moaxcp.x11protocol.generator.Conventions.getEventJavaName
import static com.github.moaxcp.x11protocol.generator.Conventions.getEventTypeName

class JavaEvent extends JavaBaseObject {

    static JavaEvent javaEvent(XTypeEvent event) {
        String simpleName = getEventJavaName(event.name)

        JavaEvent javaEvent = new JavaEvent(
            superType: event.superType,
            basePackage: event.basePackage,
            javaPackage: event.javaPackage,
            simpleName:simpleName,
            className:getEventTypeName(event.javaPackage, event.name)
        )
        javaEvent.protocol = event.toJavaProtocol(javaEvent)
        return javaEvent
    }
}
