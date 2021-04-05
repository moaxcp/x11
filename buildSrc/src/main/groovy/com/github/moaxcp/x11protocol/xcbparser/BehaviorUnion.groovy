package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.getEnumClassName
import static com.github.moaxcp.x11protocol.generator.Conventions.getStructTypeName

class BehaviorUnion extends JavaUnion {
    @Override
    TypeSpec getTypeSpec() {
        return TypeSpec.interfaceBuilder(className)
            .addModifiers(Modifier.PUBLIC)
            .addMethod(readMethod)
            .addMethod(writeMethod)
            .build()
    }

    CodeBlock makeTypeCase(ClassName type, String item) {
        CodeBlock.of('case $T.$L:\n', type, item)
    }

    @Override
    MethodSpec getReadMethod() {
        ClassName behaviorType = getEnumClassName(javaPackage, 'BehaviorType')
        ClassName defaultBehavior = getStructTypeName(javaPackage, 'DefaultBehavior')
        ClassName radioGroupBehavior = getStructTypeName(javaPackage, 'RadioGroupBehavior')
        ClassName overlayBehavior = getStructTypeName(javaPackage, 'OverlayBehavior')
        ClassName commonBehavior = getStructTypeName(javaPackage, 'CommonBehavior')

        CodeBlock code = CodeBlock.builder()
            .addStatement('byte type = in.readCard8()')
            .addStatement('byte data = in.readCard8()')
            .addStatement('$1T typeEnum = $1T.getByCode(type)', behaviorType)
            .beginControlFlow('if(typeEnum == $1T.DEFAULT || typeEnum == $1T.LOCK || typeEnum == $1T.PERMAMENT_LOCK)', behaviorType)
            .addStatement('return new $T(type)', defaultBehavior)
            .endControlFlow()
            .beginControlFlow('if(typeEnum == $1T.RADIO_GROUP || typeEnum == $1T.PERMAMENT_RADIO_GROUP)', behaviorType)
            .addStatement('return new $T(type, data)', radioGroupBehavior)
            .endControlFlow()
            .beginControlFlow('if(typeEnum == $1T.OVERLAY1 || typeEnum == $1T.OVERLAY2 || typeEnum == $1T.PERMAMENT_OVERLAY1 || typeEnum == $1T.PERMAMENT_OVERLAY2)', behaviorType)
            .addStatement('return new $T(type, data)', overlayBehavior)
            .endControlFlow()
            .beginControlFlow('else')
            .addStatement('return new $T(type, data)', commonBehavior)
            .endControlFlow()
            .build()

        return MethodSpec.methodBuilder("read${simpleName}")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(className)
            .addParameter(ClassName.get(basePackage, 'X11Input'), 'in')
            .addException(IOException)
            .addCode(code)
            .build()
    }

    @Override
    MethodSpec getWriteMethod() {
        return MethodSpec.methodBuilder("write")
        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
        .addParameter(ClassName.get(basePackage, 'X11Output'), 'out')
        .addException(IOException)
        .build()
    }
}
