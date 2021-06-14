package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.ClassName

import static com.github.moaxcp.x11protocol.generator.Conventions.getStructTypeName

class JavaStruct extends JavaObjectType {

    JavaStruct(Map map) {
        super(map)
    }

    static List<JavaStruct> javaStruct(XTypeStruct struct) {
        List<ClassName> cases = struct.getCaseClassNames()
        if(cases) {
            ClassName superType = struct.getCaseSuperName()
            return cases.collect {
                JavaStruct javaType = new JavaStruct(
                        result: struct.result,
                        superTypes: struct.superTypes + superType,
                        basePackage: struct.basePackage,
                        javaPackage: struct.javaPackage,
                        className: it
                )
                javaType.protocol = struct.toJavaProtocol(javaType)
                return javaType
            }
        }
        JavaStruct javaType = new JavaStruct(
            result: struct.result,
            superTypes: struct.superTypes + ClassName.get(struct.basePackage, 'XStruct'),
            basePackage: struct.basePackage,
            javaPackage: struct.javaPackage,
            className:getStructTypeName(struct.javaPackage, struct.name)
        )

        javaType.protocol = struct.toJavaProtocol(javaType)
        return [javaType]
    }
}
