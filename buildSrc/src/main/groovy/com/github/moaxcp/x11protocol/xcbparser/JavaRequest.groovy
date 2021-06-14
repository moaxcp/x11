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

    static List<JavaRequest> javaRequest(XTypeRequest request) {
        List<ClassName> cases = request.getCaseClassNames()
        if(cases) {
            ClassName superType = getRequestTypeName(request.javaPackage, request.name)
            return cases.collect {
                JavaRequest javaRequest = new JavaRequest(
                        result: request.result,
                        superTypes: request.superTypes + superType,
                        basePackage: request.basePackage,
                        javaPackage: request.javaPackage,
                        className: it,
                        opCode: request.opCode,
                        reply: request.reply
                )
                return setProtocol(request, javaRequest)
            }
        }

        Set<ClassName> superTypes = request.superTypes
        if(request.reply) {
            if(request.reply.caseSuperName.isPresent()) {
                superTypes += ParameterizedTypeName.get(ClassName.get(request.basePackage, 'TwoWayRequest'), request.reply.caseSuperName.get())
            } else if(request.reply.javaType.size() == 1) {
                superTypes += ParameterizedTypeName.get(ClassName.get(request.basePackage, 'TwoWayRequest'), request.reply.javaType[0].className)
            } else {
                throw new IllegalStateException("reply cannot have multiple javaTypes without a caseSuperName")
            }
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
        return [setProtocol(request, javaRequest)]
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
            if(reply.getCaseSuperName().isPresent()) {
                typeBuilder.addMethod(MethodSpec.methodBuilder('getReplyFunction')
                    .returns(ParameterizedTypeName.get(ClassName.get('com.github.moaxcp.x11client.protocol', 'XReplyFunction'), reply.getCaseSuperName().get()))
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement('return (field, sequenceNumber, in) -> $T.read$L(field, sequenceNumber, in)', reply.getCaseSuperName().get(), reply.getCaseSuperName().get().simpleName())
                    .build())
            } else if(reply.javaType.size() == 1) {
                typeBuilder.addMethod(MethodSpec.methodBuilder('getReplyFunction')
                    .returns(ParameterizedTypeName.get(ClassName.get('com.github.moaxcp.x11client.protocol', 'XReplyFunction'), reply.javaType[0].className))
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement('return (field, sequenceNumber, in) -> $T.read$L(field, sequenceNumber, in)', reply.javaType[0].className, reply.javaType[0].simpleName)
                    .build())
            } else {
                throw new IllegalStateException("only one case allowed when there is no caseSuperName")
            }
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
