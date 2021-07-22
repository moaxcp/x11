package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.xcbparser.expression.EmptyExpression
import com.github.moaxcp.x11protocol.xcbparser.expression.ParamRefExpression
import com.squareup.javapoet.*

import javax.lang.model.element.Modifier

abstract class JavaObjectType implements JavaType {
    XResult result
    String basePackage
    String javaPackage
    Set<ClassName> superTypes = []
    private String xUnitSubtype
    ClassName className
    List<JavaReadParameter> readParamInput
    List<JavaUnit> protocol

    @Override
    Optional<String> getXUnitSubtype() {
        return Optional.ofNullable(xUnitSubtype)
    }

    JavaObjectType(Map map) {
        result = map.result
        basePackage = map.basePackage
        javaPackage = map.javaPackage
        superTypes = map.superTypes ? map.superTypes + ClassName.get(javaPackage, result.getPluginXObjectInterfaceName()) : [ClassName.get(javaPackage, result.getPluginXObjectInterfaceName())]
        className = map.className
        xUnitSubtype = map.xUnitSubtype
        setProtocol(map.prtocol)
    }

    void setProtocol(List<JavaUnit> protocol) {
        this.protocol = protocol
        readParamInput = protocol.collect {JavaUnit property ->
            if(property instanceof JavaListProperty) {
                List<ParamRefExpression> paramRefs = property.lengthExpression?.paramRefs
                return paramRefs.collect {
                    XUnitField field = new XUnitField(result: property.x11Field.result, name: it.paramName, type: it.x11Type)
                    return field.getJavaUnit(this)
                }
            }
            return []
        }.flatten()
    }

    String getSimpleName() {
        return className.simpleName()
    }

    ClassName getBuilderClassName() {
        ClassName.get(javaPackage, "${simpleName}.${simpleName}Builder")
    }

    List<JavaProperty> getProperties() {
        return protocol.findAll {
            it instanceof JavaProperty
        }.collect {
            (JavaProperty) it
        }
    }

    List<JavaReadParameter> getReadParameters() {
        return readParamInput + protocol.findAll {
            it instanceof JavaReadParameter && it.readParam
        }.collect {
            (JavaReadParameter) it
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
        TypeSpec.Builder builder = TypeSpec.classBuilder(ClassName.get(javaPackage, "${simpleName}Builder"))
            .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
        builder.addMethods(getBuilderMethods())
        CodeBlock sizes = getSizeExpression()
        builder.addMethod(MethodSpec.methodBuilder('getSize')
            .addModifiers(Modifier.PUBLIC)
            .returns(TypeName.INT)
            .addStatement('return $L', sizes)
            .build())
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
        List<ParameterSpec> readParams = readParameters.collect {
            return ParameterSpec.builder(it.readTypeName, it.name).build()
        }
        methodBuilder.addParameters(readParams)
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
        builder.addStatement('$T $L = $T.builder()', builderClassName, 'javaBuilder', className)
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
                writeProtocol.beginControlFlow('if($T.$L.enabledFor($L))', it.bitcaseInfo.enumType, it.bitcaseInfo.enumItem, it.bitcaseInfo.maskField.getExpression(TypeName.INT))
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
        if(fixedSize) {
            return CodeBlock.of("${fixedSize.get()}")
        }
        int fixedSizes = protocol.stream()
            .filter({it.fixedSize.isPresent()})
            .mapToInt{it.fixedSize.get()}
            .sum()

        CodeBlock dynamicSizes = protocol.stream()
            .filter { !it.fixedSize.isPresent() }
            .map{it.getSizeExpression()}
            .collect(CodeBlock.joining(' + '))

        return CodeBlock.of('$L + $L', fixedSizes, dynamicSizes)
    }

    Optional<Integer> getFixedSize() {
        boolean empty = protocol.find {
            it.fixedSize.isEmpty()
        }
        if(empty) {
            return Optional.empty()
        }
        boolean bitcase = protocol.find {
            it instanceof JavaProperty && it.bitcaseInfo
        }
        if(bitcase) {
            return Optional.empty()
        }
        Optional.of(protocol.stream().mapToInt({it.fixedSize.get()}).sum())
    }
}
