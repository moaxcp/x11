package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.ClassName

import static com.github.moaxcp.x11protocol.generator.Conventions.getStructJavaName
import static com.github.moaxcp.x11protocol.generator.Conventions.getStructTypeName

class JavaStruct extends JavaObjectType {

    static JavaStruct javaStruct(XTypeStruct struct) {
        String simpleName = getStructJavaName(struct.name)
        JavaStruct javaType = new JavaStruct(
            superTypes: struct.superTypes + ClassName.get(struct.basePackage, 'XObject'),
            basePackage: struct.basePackage,
            javaPackage: struct.javaPackage,
            simpleName:simpleName,
            className:getStructTypeName(struct.javaPackage, struct.name)
        )

        javaType.protocol = struct.toJavaProtocol(javaType)
        return javaType
    }
}
