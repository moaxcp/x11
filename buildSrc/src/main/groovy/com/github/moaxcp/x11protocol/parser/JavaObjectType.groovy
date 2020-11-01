package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.EmptyExpression
import com.github.moaxcp.x11protocol.parser.expression.ParamRefExpression
import com.squareup.javapoet.*
import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.x11PrimativeToStorageTypeName

abstract class JavaObjectType implements JavaType {
    String basePackage
    String javaPackage
    String simpleName
    Set<ClassName> superTypes = []
    ClassName className
    List<JavaUnit> protocol

    ClassName getBuilderClassName() {
        ClassName.get(javaPackage, "${simpleName}Builder")
    }

    JavaProperty getField(String name) {
        (JavaProperty) protocol.find {
            it instanceof JavaProperty &&
            it.name == name
        }
    }

    boolean hasFields() {
        protocol.find {
            it instanceof JavaProperty
        }
    }
    
    boolean isLastListNoLength() {
        if(protocol.isEmpty()) {
            return false
        }
        JavaUnit unit = protocol.last()
        return unit instanceof JavaListProperty && unit.lengthExpression instanceof EmptyExpression
    }

    @Override
    TypeSpec getTypeSpec() {
        TypeSpec.Builder typeSpec = TypeSpec.classBuilder(className)
        typeSpec.addModifiers(Modifier.PUBLIC)
        addFields(typeSpec)
        addMethods(typeSpec)
        if(superTypes) {
            typeSpec.addSuperinterfaces(superTypes)
        }
        if(hasFields()) {
            typeSpec.addAnnotation(ClassName.get('lombok', 'Value'))
                .addAnnotation(ClassName.get('lombok', 'Builder'))
            addBuilder(typeSpec)
        }

        typeSpec.build()
    }

    void addBuilder(TypeSpec.Builder parent) {
        if(!builderMethods) {
            return
        }
        TypeSpec.Builder builder = TypeSpec.classBuilder(builderClassName)
            .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
        builder.addMethods(getBuilderMethods())
        parent.addType(builder.build())
    }
    
    List<MethodSpec> getBuilderMethods() {
        return protocol.findAll {
            it instanceof JavaProperty
        }.collect { JavaProperty it ->
            it.getBuilderMethods(className)
        }.flatten()
    }

    void addFields(TypeSpec.Builder typeBuilder) {
        List<FieldSpec> fields = protocol.findAll {
            it instanceof JavaProperty && !it.localOnly
        }.collect { JavaProperty it ->
            it.member
        }
        typeBuilder.addFields(fields)
    }

    void addMethods(TypeSpec.Builder typeBuilder) {
        List<MethodSpec> methods = protocol.findAll {
            it instanceof JavaProperty
        }.collect { JavaProperty it ->
            it.methods
        }.flatten()

        typeBuilder.addMethod(readMethod)
        .addMethod(writeMethod)
        .addMethods(methods)
        
        addSizeMethod(typeBuilder)
    }

    MethodSpec getReadMethod() {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("read${simpleName}")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(className)
            .addException(IOException)
        addReadParameters(methodBuilder)
        methodBuilder.addParameter(ClassName.get(basePackage, 'X11Input'), 'in')
        addHeaderStatements(methodBuilder)
        addReadStatements(methodBuilder)
        addBuilderStatement(methodBuilder)
        methodBuilder.addStatement('return $L', 'javaObject')

        return methodBuilder.build()
    }
    
    void addReadParameters(MethodSpec.Builder methodBuilder) {
        List<ParameterSpec> readParams = protocol.findAll {
            it.readParam
        }.collect {
            if(it instanceof JavaEnumProperty) {
                return ParameterSpec.builder(it.ioTypeName, it.name).build()
            } else if(it instanceof JavaProperty) {
                return ParameterSpec.builder(it.typeName, it.name).build()
            } else if(it instanceof JavaPad) {
                if(it.bytes == 1) {
                    return ParameterSpec.builder(TypeName.BYTE, 'pad').build()
                } else {
                    throw new IllegalStateException("found pad but ${it.bytes} bytes not supported")
                }
            } else {
                throw new IllegalStateException("${it.getClass().simpleName} not supported.")
            }
        }
        List<ParameterSpec> params = protocol.findAll {
            it instanceof JavaListProperty
        }.collect { JavaListProperty it ->
            it.lengthExpression.paramRefs
        }.flatten().collect { ParamRefExpression it ->
            ParameterSpec.builder(x11PrimativeToStorageTypeName(it.x11Type), it.paramName).build()
        }
        methodBuilder.addParameters(readParams).addParameters(params)
    }

    void addHeaderStatements(MethodSpec.Builder builder) {

    }

    void addReadStatements(MethodSpec.Builder methodBuilder) {
        CodeBlock.Builder readProtocol = CodeBlock.builder()
        protocol.each {
            if(!it.readParam) {
                readProtocol.add(it.readCode)
            }
        }

        methodBuilder.addCode(readProtocol.build())
    }
    
    void addBuilderStatement(MethodSpec.Builder method, CodeBlock... fields) {
        if(protocol.findAll{it instanceof JavaProperty}.isEmpty()) {
            method.addStatement('$1T $2L = new $1T()', className, 'javaObject')
            return
        }
        CodeBlock.Builder builder = CodeBlock.builder()
        builder.add('$1T $2L = $1T.builder()', className, 'javaObject')
        protocol.findAll {
            it instanceof JavaProperty && !it.localOnly
        }.each { JavaProperty it ->
            builder.add('\n.$L($L)', it.name, it.name)
        }
        fields.each {
            builder.add('\n$L', it)
        }
        builder.add('\n.build()')
        method.addStatement(builder.build())
    }

    MethodSpec getWriteMethod() {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("write")
            .addAnnotation(Override)
            .addModifiers(Modifier.PUBLIC)
            .addException(IOException)
        addWriteParameters(methodBuilder)
        methodBuilder.addParameter(ClassName.get(basePackage, 'X11Output'), 'out')
        addWriteStatements(methodBuilder)
        
        return methodBuilder.build()
    }
    
    void addWriteParameters(MethodSpec.Builder methodBuilder) {
        
    }
    
    void addWriteStatements(MethodSpec.Builder methodBuilder) {
        CodeBlock.Builder writeProtocol = CodeBlock.builder()
        protocol.each {
            writeProtocol.add(it.writeCode)
        }
        methodBuilder.addCode(writeProtocol.build())
    }
    
    void addSizeMethod(TypeSpec.Builder typeBuilder) {
        CodeBlock sizes = getSizeExpression()
        typeBuilder.addMethod(MethodSpec.methodBuilder('getSize')
            .addAnnotation(Override)
            .addModifiers(Modifier.PUBLIC)
            .returns(TypeName.INT)
            .addStatement('return $L', sizes)
            .build())
    }
    
    CodeBlock getSizeExpression() {
        if(protocol.isEmpty()) {
            return CodeBlock.of('0')
        }
        protocol.stream()
            .map({it.getSizeExpression()})
            .collect(CodeBlock.joining(' + '))
    }

    Optional<Integer> getFixedSize() {
        boolean empty = protocol.find {
            it.fixedSize.isEmpty()
        }
        if(empty) {
            return Optional.empty()
        }
        Optional.of(protocol.stream().mapToInt({it.fixedSize.get()}).sum())
    }
}
