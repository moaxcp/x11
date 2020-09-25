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
    void addReadStatements(MethodSpec.Builder methodBuilder) {
        if(lastListNoLength) {
            methodBuilder.addStatement('int javaStart = 1')
            methodBuilder.addCode(protocol[0].readCode)
            methodBuilder.addStatement('javaStart++')
            methodBuilder.addStatement('short length = in.readCard16()')
            methodBuilder.addStatement('javaStart += 2')
            protocol.eachWithIndex { it, i ->
                if (i == 0) {
                    return
                }
                methodBuilder.addCode(it.readCode)
                if(i != protocol.size() - 1) {
                    methodBuilder.addStatement('javaStart += $L', it.getSizeExpression())
                }
            }
        } else {
            if(protocol) {
                methodBuilder.addCode(protocol[0].readCode)
            } else {
                methodBuilder.addStatement('in.readByte()')
            }
            methodBuilder.addStatement('short length = in.readCard16()')
            CodeBlock.Builder readProtocol = CodeBlock.builder()
            protocol.eachWithIndex { it, i ->
                if (i == 0) {
                    return
                }
                readProtocol.add(it.readCode)
            }
            methodBuilder.addCode(readProtocol.build())
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

    @Override
    void addWriteStatements(MethodSpec.Builder methodBuilder) {
        methodBuilder.addStatement('out.writeCard8(OPCODE)')
        if(protocol) {
            methodBuilder.addCode(protocol[0].writeCode)
        } else {
            methodBuilder.addStatement('out.writePad(1)')
        }
        methodBuilder.addStatement('out.writeCard16((short) getLength())')
        CodeBlock.Builder writeProtocol = CodeBlock.builder()
        protocol.eachWithIndex { it, i ->
            if(i == 0) {
                return
            }
            writeProtocol.add(it.writeCode)
        }
        methodBuilder.addCode(writeProtocol.build())
        if(fixedSize && fixedSize.get() % 4 == 0) {
            return
        }
        methodBuilder.addStatement('out.writePadAlign(getSize())')
    }

    @Override
    CodeBlock getSizeExpression() {
        CodeBlock.of('$L + $L',
            CodeBlock.of('1 + 2'),
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
        Optional.of(protocol.stream().mapToInt({it.fixedSize.get()}).sum() + 3)
    }
}
