package com.github.moaxcp.x11protocol.parser


import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.fromUpperCamelToLowerCamel
import static com.github.moaxcp.x11protocol.generator.Conventions.getUnionJavaName
import static com.github.moaxcp.x11protocol.generator.Conventions.getUnionTypeName

class JavaUnion extends JavaBaseObject {

    static JavaUnion javaUnion(XTypeUnion union) {
        List<JavaUnit> protocol = union.toJavaProtocol()
        String simpleName = getUnionJavaName(union.name)
        return "${fromUpperCamelToLowerCamel(simpleName)}JavaUnion"(
            basePackage: union.basePackage,
            simpleName: simpleName,
            className: getUnionTypeName(union.javaPackage, union.name),
            protocol: protocol
        )
    }

    static JavaUnion behaviorUnionJavaUnion(Map args) {
        return new BehaviorUnion(args)
    }

    static JavaUnion clientMessageDataUnionJavaUnion(Map args) {
        return new JavaUnion(args)
    }

    static JavaUnion actionUnionJavaUnion(Map args) {
        return new JavaUnion(args)
    }

    static JavaUnion notifyDataUnionJavaUnion(Map args) {
        return new JavaUnion(args)
    }

    @Override
    TypeSpec getTypeSpec() {
        return TypeSpec.interfaceBuilder(className)
            .addModifiers(Modifier.PUBLIC)
            .build()
    }

    @Override
    MethodSpec getReadMethod() {
        return null
    }

    @Override
    MethodSpec getWriteMethod() {
        return null
    }
}
