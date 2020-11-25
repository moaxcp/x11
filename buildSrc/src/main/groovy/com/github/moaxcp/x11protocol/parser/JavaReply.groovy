package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.*

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
        if(javaReply.protocol.size() > 0) {
            javaReply.protocol[0].readParam = true
            javaReply.protocol[1].readParam = true
        }
        return javaReply
    }

    @Override
    void addReadParameters(MethodSpec.Builder methodBuilder) {
        if(protocol.size() > 0) {
            if(protocol[0] instanceof JavaPad) {
                methodBuilder.addParameter(ParameterSpec.builder(TypeName.BYTE, 'pad').build())
            } else {
                methodBuilder.addParameter(ParameterSpec.builder(TypeName.BYTE, ((JavaProperty) protocol[0]).name).build())
            }
            methodBuilder.addParameter(ParameterSpec.builder(TypeName.SHORT, 'sequenceNumber').build())
        }
    }

    @Override
    void addReadStatements(MethodSpec.Builder methodBuilder) {
        if(lastListNoLength) {
            methodBuilder.addStatement('int javaStart = 1')
            protocol.eachWithIndex { it, i ->
                methodBuilder.addCode(it.declareAndReadCode)
                if(i != protocol.size() - 1) {
                    methodBuilder.addStatement('javaStart += $L', it.getSizeExpression())
                }
            }
        } else {
            if(protocol.size() > 1) {
                super.addReadStatements(methodBuilder)
            } else {
                methodBuilder.addStatement('int length = in.readCard32()')
            }
        }
    }

    @Override
    void addBuilderStatement(MethodSpec.Builder methodBuilder, CodeBlock... fields) {
        CodeBlock.Builder builder = CodeBlock.builder()
        builder.addStatement('$1T $2L = $1T.builder()', className, 'javaBuilder')
        protocol.findAll {
            it instanceof JavaProperty && !it.localOnly
        }.eachWithIndex { JavaProperty it, int i ->
            if(i == 0 && it.typeName == TypeName.BOOLEAN) {
                builder.addStatement('javaBuilder.$L($L > 0)', it.name, it.name)
            } else if (i == 0 && it instanceof JavaEnumProperty) {
                builder.addStatement('javaBuilder.$L($T.getByCode($L))', it.name, it.typeName, it.name)
            } else {
                builder.addStatement('javaBuilder.$L($L)', it.name, it.name)
            }
        }
        methodBuilder.addCode(builder.build())
        if(fixedSize && fixedSize.get() < 32) {
            methodBuilder.addStatement('in.readPad($L)', 32 - fixedSize.get())
            return
        } else if(!fixedSize) {
            methodBuilder.beginControlFlow('if(javaBuilder.getSize() < 32)')
            methodBuilder.addStatement('in.readPad(32 - javaBuilder.getSize())')
            methodBuilder.endControlFlow()
            return
        }

        if(fixedSize && fixedSize.get() % 4 == 0) {
            return
        }
        methodBuilder.addStatement('in.readPadAlign(javaObject.getSize())')
    }

    @Override
    void addWriteStatements(MethodSpec.Builder methodBuilder) {
        CodeBlock.Builder writeProtocol = CodeBlock.builder()
        writeProtocol.addStatement('out.writeCard8((byte) 1)')
        if(protocol.size() > 1) {
            protocol.each { it ->
                if(it instanceof JavaProperty && it.name == 'length') {
                    writeProtocol.addStatement('out.writeCard32(getLength())')
                } else if(it instanceof JavaProperty && it.bitcaseInfo) {
                    writeProtocol.beginControlFlow('if(is$LEnabled($T.$L)', it.bitcaseInfo.maskField.capitalize(), it.bitcaseInfo.enumType, it.bitcaseInfo.enumItem)
                    it.addWriteCode(writeProtocol)
                    writeProtocol.endControlFlow()
                } else {
                    it.addWriteCode(writeProtocol)
                }
            }
        } else {
            writeProtocol.addStatement('out.writePad(1)')
            writeProtocol.addStatement('out.writeCard32(0)')
        }
        if(fixedSize && fixedSize.get() % 4 == 0) {
            methodBuilder.addCode(writeProtocol.build())
            return
        }
        writeProtocol.addStatement('out.writePadAlign(getSize())')
        methodBuilder.addCode(writeProtocol.build())
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
