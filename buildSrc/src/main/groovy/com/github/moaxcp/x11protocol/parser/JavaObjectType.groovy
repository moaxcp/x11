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

    List<JavaProperty> getProperties() {
        return protocol.findAll {
            it instanceof JavaProperty
        }.collect {
            (JavaProperty) it
        }
    }

    JavaProperty getJavaProperty(String name) {
        return properties.find {
            it.name == name
        }
    }

    boolean hasFields() {
        return properties
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
        TypeSpec.Builder builder = TypeSpec.classBuilder(builderClassName)
            .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
        builder.addMethods(getBuilderMethods())
        addSizeMethod(builder)
        parent.addType(builder.build())
    }
    
    List<MethodSpec> getBuilderMethods() {
        return properties.collect {
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
        List<MethodSpec> methods = properties.collect {
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
        if(properties.isEmpty()) {
            methodBuilder.addStatement('return new $T()', className)
            return methodBuilder.build()
        }
        addBuilderStatement(methodBuilder)
        methodBuilder.addStatement('return $L', 'javaBuilder.build()')

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
        List<ParameterSpec> params = properties.findAll {
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
            if(!it.readProtocol
                || (it instanceof JavaProperty && it.bitcaseInfo)) {
                return
            }
            readProtocol.add(it.declareAndReadCode)
        }

        methodBuilder.addCode(readProtocol.build())
    }
    
    void addBuilderStatement(MethodSpec.Builder method, CodeBlock... fields) {
        CodeBlock.Builder builder = CodeBlock.builder()
        builder.addStatement('$1T $2L = $1T.builder()', builderClassName, 'javaBuilder')
        properties.each {
            it.addBuilderCode(builder)
        }
        fields.each {
            builder.add('\n$L', it)
        }
        method.addCode(builder.build())
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
            if(it instanceof JavaProperty && it.bitcaseInfo) {
                writeProtocol.beginControlFlow('if(is$LEnabled($T.$L)', it.bitcaseInfo.maskField.capitalize(), it.bitcaseInfo.enumType, it.bitcaseInfo.enumItem)
                it.addWriteCode(writeProtocol)
                writeProtocol.endControlFlow()
            } else {
                it.addWriteCode(writeProtocol)
            }
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
