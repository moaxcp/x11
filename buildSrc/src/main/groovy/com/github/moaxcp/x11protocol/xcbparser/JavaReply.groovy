package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.getReplyTypeName

class JavaReply extends JavaClass {
    JavaReply(Map map) {
        super(map)
    }

    static JavaReply javaReply(XTypeReply reply) {
        JavaReply javaReply = new JavaReply(
            result: reply.result,
            superTypes: reply.superTypes + ClassName.get(reply.basePackage, 'XReply'),
            basePackage: reply.basePackage,
            javaPackage: reply.javaPackage,
            className: getReplyTypeName(reply.javaPackage, reply.name)
        )
        return setProtocol(reply, javaReply)
    }

    static JavaType javaReply(XTypeReply reply, String subType) {
        ClassName replyClass = getReplyTypeName(reply.javaPackage, reply.name + subType.capitalize())
        ClassName superType = getReplyTypeName(reply.javaPackage, reply.name)

        JavaReply javaType = new JavaReply(
            result: reply.result,
            superTypes: reply.superTypes + superType,
            xUnitSubtype: subType,
            basePackage: reply.basePackage,
            javaPackage: reply.javaPackage,
            className: replyClass,
        )
        return setProtocol(reply, javaType)
    }

    private static JavaReply setProtocol(XTypeReply reply, JavaReply javaReply) {
        javaReply.protocol = reply.toJavaProtocol(javaReply)
        JavaProperty r = javaReply.getJavaProperty('RESPONSECODE')
        r.constantField = true
        r.localOnly = true
        r.writeValueExpression = CodeBlock.of('getResponseCode()')
        JavaProperty l = javaReply.getJavaProperty('length')
        l.writeValueExpression = CodeBlock.of('getLength()')
        if (!(javaReply.protocol[1] instanceof JavaReadParameter)) {
            throw new IllegalStateException("First field in ${javaReply.simpleName} must be a JavaReadParameter. got ${javaReply.protocol[1]}")
        }
        JavaReadParameter first = (JavaReadParameter) javaReply.protocol[1]
        first.readParam = true
        first.readTypeName = TypeName.BYTE
        if (!(javaReply.protocol[2] instanceof JavaReadParameter)) {
            throw new IllegalStateException("Second field must be a JavaReadParameter")
        }
        ((JavaReadParameter) javaReply.protocol[2]).readParam = true
        return javaReply
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
        if(fixedSize && fixedSize.get() < 32) {
            methodBuilder.addStatement('in.readPad($L)', 32 - fixedSize.get())
        }
    }

    @Override
    void addBuilderStatement(MethodSpec.Builder methodBuilder, CodeBlock... fields) {
        super.addBuilderStatement(methodBuilder, fields)
        if(!fixedSize) {
            methodBuilder.beginControlFlow('if(javaBuilder.getSize() < 32)')
            methodBuilder.addStatement('in.readPad(32 - javaBuilder.getSize())')
            methodBuilder.endControlFlow()
            return
        }

        if(fixedSize && fixedSize.get() % 4 == 0) {
            return
        }
        methodBuilder.addStatement('in.readPadAlign(javaBuilder.getSize())')
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
