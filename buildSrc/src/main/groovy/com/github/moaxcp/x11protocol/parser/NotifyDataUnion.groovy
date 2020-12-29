package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.*
import javax.lang.model.element.Modifier

class NotifyDataUnion extends JavaUnion {
    @Override
    TypeSpec getTypeSpec() {
        return TypeSpec.interfaceBuilder(className)
            .addModifiers(Modifier.PUBLIC)
            .addSuperinterface(ClassName.get(basePackage, 'XObject'))
            .addMethod(readMethod)
            .addMethod(MethodSpec.methodBuilder('write')
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addParameter(ParameterSpec.builder(ClassName.get(basePackage, 'X11Output'), 'in').build())
                .addException(IOException)
                .build())
            .build()
    }

    @Override
    MethodSpec getReadMethod() {
        return MethodSpec.methodBuilder("read${simpleName}")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(className)
            .addParameter(ClassName.get(basePackage, 'X11Input'), 'in')
            .addParameter(TypeName.BYTE, 'subCode')
            .addException(IOException)
            .addCode(CodeBlock.builder()
                .beginControlFlow('switch(subCode)')
                .add('case 0:\n')
                .indent()
                .addStatement('return $T.read$L(in)', ClassName.get(javaPackage, 'CrtcChange'), 'CrtcChange')
                .unindent()
                .add('case 1:\n')
                .indent()
                .addStatement('return $T.read$L(in)', ClassName.get(javaPackage, 'OutputChange'), 'OutputChange')
                .unindent()
                .add('case 2:\n')
                .indent()
                .addStatement('return $T.read$L(in)', ClassName.get(javaPackage, 'OutputProperty'), 'OutputProperty')
                .unindent()
                .add('case 3:\n')
                .indent()
                .addStatement('return $T.read$L(in)', ClassName.get(javaPackage, 'ProviderChange'), 'ProviderChange')
                .unindent()
                .add('case 4:\n')
                .indent()
                .addStatement('return $T.read$L(in)', ClassName.get(javaPackage, 'ProviderProperty'), 'ProviderProperty')
                .unindent()
                .add('case 5:\n')
                .indent()
                .addStatement('return $T.read$L(in)', ClassName.get(javaPackage, 'ResourceChange'), 'ResourceChange')
                .unindent()
                .add('case 6:\n')
                .indent()
                .addStatement('return $T.read$L(in)', ClassName.get(javaPackage, 'LeaseNotify'), 'LeaseNotify')
                .unindent()
                .add('default:\n')
                .indent()
                .addStatement('throw new IllegalArgumentException("implementation not found for subCode \\"" + subCode + "\\"")')
                .unindent()
                .endControlFlow()
                .build()
            )
            .build()
    }

    @Override
    MethodSpec getWriteMethod() {
        return null
    }
}
