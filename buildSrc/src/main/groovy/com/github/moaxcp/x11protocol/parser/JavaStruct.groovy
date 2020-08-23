package com.github.moaxcp.x11protocol.parser


import static com.github.moaxcp.x11protocol.generator.Conventions.getStructJavaName
import static com.github.moaxcp.x11protocol.generator.Conventions.getStructTypeName

class JavaStruct extends JavaBaseObject {

    static JavaStruct javaStruct(XTypeStruct struct) {
        String simpleName = getStructJavaName(struct.name)
        JavaStruct javaType = new JavaStruct(
            superType: struct.superType,
            basePackage: struct.basePackage,
            javaPackage: struct.javaPackage,
            simpleName:simpleName,
            className:getStructTypeName(struct.javaPackage, struct.name)
        )

        javaType.protocol = struct.toJavaProtocol(javaType)
        return javaType
    }
}
