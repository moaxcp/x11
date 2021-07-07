package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.ClassName

import static com.github.moaxcp.x11protocol.generator.Conventions.getJavaName
import static com.github.moaxcp.x11protocol.generator.Conventions.getStructTypeName

class JavaStruct extends JavaObjectType {

    JavaStruct(Map map) {
        super(map)
    }

    static JavaStruct javaStruct(XTypeStruct struct) {
        JavaStruct javaType = new JavaStruct(
            result: struct.result,
            superTypes: struct.superTypes + ClassName.get(struct.basePackage, 'XStruct'),
            basePackage: struct.basePackage,
            javaPackage: struct.javaPackage,
            className:getStructTypeName(struct.javaPackage, struct.name)
        )

        javaType.protocol = struct.toJavaProtocol(javaType)
        return javaType
    }

    static JavaType javaStruct(XTypeStruct struct, String subType) {
        ClassName structClass = getStructTypeName(struct.javaPackage, struct.name + getJavaName(subType))
        ClassName superType = getStructTypeName(struct.javaPackage, struct.name)

        JavaStruct javaType = new JavaStruct(
            result: struct.result,
            superTypes: struct.superTypes + superType,
            xUnitSubtype: subType,
            basePackage: struct.basePackage,
            javaPackage: struct.javaPackage,
            className: structClass
        )

        javaType.protocol = struct.toJavaProtocol(javaType)
        return javaType
    }
}
