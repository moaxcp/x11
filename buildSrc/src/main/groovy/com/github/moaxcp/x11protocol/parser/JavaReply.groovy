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
                methodBuilder.addCode(it.readCode)
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
    void addSetterStatements(MethodSpec.Builder methodBuilder) {
        CodeBlock.Builder setters = CodeBlock.builder()
        protocol.findAll {
            it instanceof JavaProperty && !it.localOnly
        }.eachWithIndex { JavaProperty it, int i ->
            if(i == 0 && it.typeName == TypeName.BOOLEAN) {
                setters.addStatement('$L.$L($L > 0)', 'javaObject', it.setterName, it.name)
            } else if (i == 0 && it instanceof JavaEnumProperty) {
                setters.addStatement('$L.$L($T.getByCode($L))', 'javaObject', it.setterName, it.typeName, it.name)
            } else {
                setters.addStatement('$L.$L($L)', 'javaObject', it.setterName, it.name)
            }
        }
        methodBuilder.addCode(setters.build())
        if(fixedSize && fixedSize.get() < 32) {
            methodBuilder.addStatement('in.readPad($L)', 32 - fixedSize.get())
            return
        } else if(!fixedSize) {
            methodBuilder.beginControlFlow('if(javaObject.getSize() < 32)')
            methodBuilder.addStatement('in.readPad(32 - javaObject.getSize())')
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
