package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.ClassName

import static com.github.moaxcp.x11protocol.generator.Conventions.getEventStructJavaName
import static com.github.moaxcp.x11protocol.generator.Conventions.getEventStructTypeName

class JavaEventStruct extends JavaClass {
    //TODO the following properties may be used for verification of the event property when adding it to another xobject
    String allowedExtension
    boolean allowGenericEvents
    int minOpcode
    int maxOpcode

    JavaEventStruct(Map map) {
        super(map)
        allowedExtension = map.allowedExtension
        allowGenericEvents = map.allowGenericEvents
        minOpcode = map.minOpcode
        maxOpcode = map.maxOpcode
    }

    static JavaEventStruct javaEventStruct(XTypeEventStruct struct) {
        String simpleName = getEventStructJavaName(struct.name)
        ClassName superType = ClassName.get(struct.basePackage, 'XEvent')
        JavaEventStruct javaEventStruct = new JavaEventStruct(
                result: struct.result,
                superTypes: [superType],
                basePackage: struct.basePackage,
                javaPackage: struct.javaPackage,
                simpleName: simpleName,
                className: getEventStructTypeName(struct.javaPackage, struct.name),
                allowedExtension: struct.allowedExtension,
                allowGenericEvents: struct.allowGenericEvents,
                minOpcode: struct.minOpcode,
                maxOpcode: struct.maxOpcode
        )
        return javaEventStruct
    }
}
