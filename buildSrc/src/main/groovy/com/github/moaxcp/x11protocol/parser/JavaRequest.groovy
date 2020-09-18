package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.*
import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.getRequestJavaName
import static com.github.moaxcp.x11protocol.generator.Conventions.getRequestTypeName

class JavaRequest extends JavaObjectType {
    int opCode

    static JavaRequest javaRequest(XTypeRequest request) {
        String simpleName = getRequestJavaName(request.name)

        JavaRequest javaRequest = new JavaRequest(
            superTypes: request.superTypes + ClassName.get(request.basePackage, 'XRequest'),
            basePackage: request.basePackage,
            javaPackage: request.javaPackage,
            simpleName:simpleName,
            className: getRequestTypeName(request.javaPackage, request.name),
            opCode: request.opCode
        )
        javaRequest.protocol = request.toJavaProtocol(javaRequest)
        return javaRequest
    }

    @Override
    void addFields(TypeSpec.Builder typeBuilder) {
        typeBuilder.addField(FieldSpec.builder(TypeName.BYTE, 'OPCODE', Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
            .initializer('$L', opCode)
            .build())
        super.addFields(typeBuilder)
    }

    @Override
    void addMethods(TypeSpec.Builder typeBuilder) {
        typeBuilder.addMethod(MethodSpec.methodBuilder('getOpCode')
            .returns(TypeName.BYTE)
            .addModifiers(Modifier.PUBLIC)
            .addStatement('return OPCODE')
            .build())

        super.addMethods(typeBuilder)
    }

    @Override
    void addWriteStatements(MethodSpec.Builder methodBuilder) {
        methodBuilder.addStatement('out.writeCard8(OPCODE)')
        super.addWriteStatements(methodBuilder)
    }
}
