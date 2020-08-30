package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.ParamRefExpression
import com.squareup.javapoet.*
import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.x11PrimativeToJavaTypeName

abstract class JavaBaseObject implements JavaType {
    String basePackage
    String javaPackage
    String simpleName
    Set<ClassName> superTypes = []
    ClassName className
    List<JavaUnit> protocol

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
            typeSpec.addAnnotation(ClassName.get('lombok', 'Data'))
                .addAnnotation(ClassName.get('lombok', 'AllArgsConstructor'))
                .addAnnotation(ClassName.get('lombok', 'NoArgsConstructor'))
        }

        typeSpec.build()
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
            .addParameter(ClassName.get(basePackage, 'X11Input'), 'in')
            .addException(IOException)
        addReadParameters(methodBuilder)
        addHeaderStatements(methodBuilder)
        addReadStatements(methodBuilder)
        methodBuilder.addStatement('$1T $2L = new $1T()', className, 'javaObject')
        addSetterStatements(methodBuilder)

        methodBuilder.addStatement('return $L', 'javaObject')

        return methodBuilder.build()
    }
    
    void addReadParameters(MethodSpec.Builder methodBuilder) {
        List<ParameterSpec> params = protocol.findAll {
            it instanceof JavaListProperty
        }.collect { JavaListProperty it ->
            it.lengthExpression.paramRefs
        }.flatten().collect { ParamRefExpression it ->
            ParameterSpec.builder(x11PrimativeToJavaTypeName(it.x11Type), it.paramName).build()
        }
        methodBuilder.addParameters(params)
    }

    void addHeaderStatements(MethodSpec.Builder builder) {

    }

    void addReadStatements(MethodSpec.Builder methodBuilder) {
        CodeBlock.Builder readProtocol = CodeBlock.builder()
        protocol.each {
            readProtocol.add(it.readCode)
        }

        methodBuilder.addCode(readProtocol.build())
    }
    
    void addSetterStatements(MethodSpec.Builder methodBuilder) {
        CodeBlock.Builder setters = CodeBlock.builder()
        protocol.findAll {
            it instanceof JavaProperty && !it.localOnly
        }.each { JavaProperty it ->
            setters.addStatement('$L.$L($L)', 'javaObject', it.setterName, it.name)
        }
        methodBuilder.addCode(setters.build())
    }

    MethodSpec getWriteMethod() {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("write")
            .addModifiers(Modifier.PUBLIC)
            .addParameter(ClassName.get(basePackage, 'X11Output'), 'out')
            .addException(IOException)
        addWriteParameters(methodBuilder)
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
            .addModifiers(Modifier.PUBLIC)
            .returns(TypeName.INT)
            .addStatement('return $L', sizes)
            .build())
    }
    
    CodeBlock getSizeExpression() {
        protocol.stream()
            .map({it.getSize()})
            .collect(CodeBlock.joining(' + '))
    }
}
