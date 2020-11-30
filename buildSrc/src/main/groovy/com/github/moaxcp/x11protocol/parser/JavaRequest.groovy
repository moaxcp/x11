package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.*
import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.getRequestJavaName
import static com.github.moaxcp.x11protocol.generator.Conventions.getRequestTypeName

class JavaRequest extends JavaObjectType {
    int opCode
    XTypeReply reply

    static JavaRequest javaRequest(XTypeRequest request) {
        String simpleName = getRequestJavaName(request.name)

        JavaRequest javaRequest = new JavaRequest(
            superTypes: request.superTypes + ClassName.get(request.basePackage, 'XRequest'),
            basePackage: request.basePackage,
            javaPackage: request.javaPackage,
            simpleName:simpleName,
            className: getRequestTypeName(request.javaPackage, request.name),
            opCode: request.opCode,
            reply: request.reply
        )
        javaRequest.protocol = request.toJavaProtocol(javaRequest)
        JavaProperty c = javaRequest.getJavaProperty('OPCODE')
        c.constantField = true
        c.writeValueExpression = CodeBlock.of('(byte)($1T.toUnsignedInt(OPCODE) + $1T.toUnsignedInt(offset))', ClassName.get('java.lang', 'Byte'))
        JavaProperty l = javaRequest.getJavaProperty('length')
        l.writeValueExpression = CodeBlock.of('(short) getLength()')
        return javaRequest
    }

    @Override
    void addMethods(TypeSpec.Builder typeBuilder) {
        CodeBlock replyReturn
        if(reply) {
            replyReturn = CodeBlock.builder().addStatement('return Optional.of((field, sequenceNumber, in) -> $T.read$L(field, sequenceNumber, in))', reply.javaType.className, reply.javaType.simpleName).build()
        } else {
            replyReturn = CodeBlock.builder().addStatement('return Optional.empty()').build()
        }
        typeBuilder.addMethod(MethodSpec.methodBuilder('getReplyFunction')
            .returns(ParameterizedTypeName.get(ClassName.get('java.util', 'Optional'), ClassName.get('com.github.moaxcp.x11client.protocol', 'XReplyFunction')))
            .addModifiers(Modifier.PUBLIC)
            .addCode(replyReturn)
            .build())
        typeBuilder.addMethod(MethodSpec.methodBuilder('getOpCode')
            .returns(TypeName.BYTE)
            .addModifiers(Modifier.PUBLIC)
            .addStatement('return OPCODE')
            .build())

        super.addMethods(typeBuilder)
    }

    @Override
    void addReadStatements(MethodSpec.Builder methodBuilder) {
        if(lastListNoLength) {
            methodBuilder.addStatement('int javaStart = 1')
            protocol.eachWithIndex { it, i ->
                if(!it.readProtocol
                    || (it instanceof JavaProperty && it.bitcaseInfo)) {
                    return
                }
                methodBuilder.addCode(it.declareAndReadCode)
                if(i != protocol.size() - 1) {
                    methodBuilder.addStatement('javaStart += $L', it.getSizeExpression())
                }
            }
        } else {
            super.addReadStatements(methodBuilder)
        }
    }

    @Override
    void addBuilderStatement(MethodSpec.Builder methodBuilder, CodeBlock... fields) {
        super.addBuilderStatement(methodBuilder)
        if(fixedSize && fixedSize.get() % 4 == 0) {
            return
        }
        methodBuilder.addStatement('in.readPadAlign(javaBuilder.getSize())')
    }

    @Override
    void addWriteParameters(MethodSpec.Builder methodBuilder) {
        methodBuilder.addParameter(byte.class, 'offset')
    }

    @Override
    void addWriteStatements(MethodSpec.Builder methodBuilder) {
        super.addWriteStatements(methodBuilder)
        if(fixedSize && fixedSize.get() % 4 == 0) {
            return
        }
        methodBuilder.addStatement('out.writePadAlign(getSize())')
    }
}
