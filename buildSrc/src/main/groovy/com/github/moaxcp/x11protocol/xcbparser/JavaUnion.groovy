package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.*

class JavaUnion extends JavaObjectType {

    JavaUnion(Map map) {
        super(map)
    }

    static JavaUnion javaUnion(XTypeUnion union) {
        String simpleName = getUnionJavaName(union.name)
        JavaUnion javaUnion = "${fromUpperCamelToLowerCamel(simpleName)}JavaUnion"(
            result: union.result,
            superTypes: union.superTypes + ClassName.get(union.basePackage, 'XObject'),
            basePackage: union.basePackage,
            javaPackage: union.javaPackage,
            simpleName: simpleName,
            className: getUnionTypeName(union.javaPackage, union.name)
        )
        javaUnion.protocol = union.toJavaProtocol(javaUnion)
        return javaUnion
    }

    static JavaUnion javaUnion(XTypeUnion union, String subType) {
        throw new UnsupportedOperationException("subtype with union")
    }

    static JavaUnion behaviorUnionJavaUnion(Map args) {
        return new BehaviorUnion(args)
    }

    static JavaUnion clientMessageDataUnionJavaUnion(Map args) {
        return new ClientMessageDataUnion(args)
    }

    static JavaUnion actionUnionJavaUnion(Map args) {
        return new JavaUnion(args)
    }

    static JavaUnion notifyDataUnionJavaUnion(Map args) {
        return new NotifyDataUnion(args)
    }

    @Override
    TypeSpec getTypeSpec() {
        return TypeSpec.interfaceBuilder(className)
            .addModifiers(Modifier.PUBLIC)
            .addSuperinterface(ClassName.get(basePackage, 'XObject'))
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
