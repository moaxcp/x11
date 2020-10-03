package com.github.moaxcp.x11protocol.parser


import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec

import static com.github.moaxcp.x11protocol.generator.Conventions.getReplyJavaName
import static com.github.moaxcp.x11protocol.generator.Conventions.getReplyTypeName

class JavaReply extends JavaObjectType {
    static JavaReply javaReply(XTypeReply reply) {
        String simpleName= getReplyJavaName(reply.name)

        JavaReply javaReply = new JavaReply(
            superTypes: reply.superTypes + ClassName.get(reply.basePackage, 'XReply'),
            basePackage: reply.basePackage,
            javaPackage: reply.javaPackage,
            simpleName:simpleName,
            className: getReplyTypeName(reply.javaPackage, reply.name)
        )
        javaReply.protocol = reply.toJavaProtocol(javaReply)
        return javaReply
    }

    @Override
    void addReadStatements(MethodSpec.Builder methodBuilder) {
        if(lastListNoLength) {
            methodBuilder.addStatement('int javaStart = 1')
            protocol.eachWithIndex { it, i ->
                methodBuilder.addCode(it.readCode)
                if(i != protocol.size() - 1) {
                    methodBuilder.addStatement('javaStart += $L', it.getSizeExpression())
                }
            }
        } else {
            if(protocol.size() > 1) {
                super.addReadStatements(methodBuilder)
            } else {
                methodBuilder.addStatement('in.readByte()')
                methodBuilder.addStatement('int length = in.readCard32()')
            }
        }
    }

    @Override
    void addSetterStatements(MethodSpec.Builder methodBuilder) {
        super.addSetterStatements(methodBuilder)
        if(fixedSize && fixedSize.get() % 4 == 0) {
            return
        }
        methodBuilder.addStatement('in.readPadAlign(javaObject.getSize())')
    }

    void addWriteParameters(MethodSpec.Builder methodBuilder) {
        methodBuilder.addParameter(ParameterSpec.builder(short.class, 'sequenceNumber').build())
        super.addReadParameters(methodBuilder)
    }

    @Override
    void addWriteStatements(MethodSpec.Builder methodBuilder) {
        methodBuilder.addStatement('out.writeCard8((byte) 1)')
        if(protocol.size() > 1) {
            CodeBlock.Builder writeProtocol = CodeBlock.builder()
            protocol.each { it ->
                if(it instanceof JavaProperty && it.name == 'length') {
                    methodBuilder.addStatement('out.writeCard32(getLength())')
                } else {
                    methodBuilder.addCode(it.writeCode)
                }
            }
            methodBuilder.addCode(writeProtocol.build())
        } else {
            methodBuilder.addStatement('out.writePad(1)')
            methodBuilder.addStatement('out.writeCard32(0)')
        }
        if(fixedSize && fixedSize.get() % 4 == 0) {
            return
        }
        methodBuilder.addStatement('out.writePadAlign(getSize())')
    }

    @Override
    CodeBlock getSizeExpression() {
        CodeBlock.of('$L + $L',
            CodeBlock.of('1'),
            super.getSizeExpression())
    }

    @Override
    Optional<Integer> getFixedSize() {
        boolean empty = protocol.find {
            it.fixedSize.isEmpty()
        }
        if(empty) {
            return Optional.empty()
        }
        Optional.of(protocol.stream().mapToInt({it.fixedSize.get()}).sum() + 1)
    }
}
