package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.*

import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.getRequestTypeName

class JavaRequest extends JavaObjectType {
    int opCode
    XTypeReply reply

    JavaRequest(Map map) {
        super(map)
        opCode = map.opCode
        reply = map.reply
    }

    static JavaRequest javaRequest(XTypeRequest request) {
        Set<ClassName> superTypes = request.superTypes
        if(request.reply) {
            superTypes += ParameterizedTypeName.get(ClassName.get(request.basePackage, 'TwoWayRequest'), request.reply.javaType.className)
        } else {
            superTypes += ClassName.get(request.basePackage, 'OneWayRequest')
        }
        JavaRequest javaRequest = new JavaRequest(
            result: request.result,
            superTypes: superTypes,
            basePackage: request.basePackage,
            javaPackage: request.javaPackage,
            className: getRequestTypeName(request.javaPackage, request.name),
            opCode: request.opCode,
            reply: request.reply
        )
        return setProtocol(request, javaRequest)
    }

    static JavaRequest javaRequest(XTypeRequest request, String subType) {
        ClassName requestClass = getRequestTypeName(request.javaPackage, request.name + subType.capitalize())
        ClassName superType = getRequestTypeName(request.javaPackage, request.name)

        JavaRequest javaType = new JavaRequest(
            result: request.result,
            superTypes: request.superTypes + superType,
            basePackage: request.basePackage,
            javaPackage: request.javaPackage,
            className: requestClass,
            opCode: request.opCode,
            reply: request.reply
        )

        return setProtocol(request, javaType)
    }

    private static JavaRequest setProtocol(XTypeRequest request, JavaRequest javaRequest) {
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
        if(reply) {
            typeBuilder.addMethod(MethodSpec.methodBuilder('getReplyFunction')
                .returns(ParameterizedTypeName.get(ClassName.get('com.github.moaxcp.x11client.protocol', 'XReplyFunction'), reply.javaType.className))
                .addModifiers(Modifier.PUBLIC)
                .addStatement('return (field, sequenceNumber, in) -> $T.read$L(field, sequenceNumber, in)', reply.javaType.className, reply.javaType.simpleName)
                .build())
        }
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
