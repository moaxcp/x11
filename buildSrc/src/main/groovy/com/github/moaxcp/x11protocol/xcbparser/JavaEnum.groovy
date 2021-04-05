package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.getEnumJavaName
import static com.github.moaxcp.x11protocol.generator.Conventions.getEnumClassName
import static com.github.moaxcp.x11protocol.generator.Conventions.getEnumValueName

class JavaEnum implements JavaType {
    String basePackage
    String javaPackage
    String simpleName
    ClassName className
    ClassName superInterface
    Map<String, String> values

    ClassName getBuilderClassName() {
        ClassName.get(javaPackage, "${simpleName}Builder")
    }

    @Override
    JavaProperty getJavaProperty(String name) {
        if(name == 'value') {
            return new JavaPrimativeProperty(
                name: 'value',
                x11Primative: 'CARD32',
                memberTypeName: TypeName.INT
            )
        }
        return null
    }

    @Override
    TypeSpec getTypeSpec() {
        return TypeSpec.enumBuilder(className)
            .addModifiers(Modifier.PUBLIC)
            .addSuperinterface(superInterface)
            .addField(FieldSpec.builder(TypeName.INT, 'value', Modifier.PRIVATE).build())
            .addMethod(MethodSpec.constructorBuilder()
                .addParameter(TypeName.INT, 'value')
                .addStatement("this.\$N = \$N", 'value', 'value')
                .build())
            .addMethod(MethodSpec.methodBuilder('getValue')
                .addAnnotation(Override)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return value")
                .returns(TypeName.INT)
                .build())
            .with(true) {TypeSpec.Builder builder ->
                values.each {
                    builder.addEnumConstant(it.key,
                        TypeSpec.anonymousClassBuilder('$L', it.value).build())
                }
                builder
            }
            .addField(FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(Integer), className), 'byCode')
                .addModifiers(Modifier.STATIC, Modifier.FINAL)
                .initializer('new $T<>()', ClassName.get(HashMap))
                .build())
            .addStaticBlock(CodeBlock.of('''\
                for($T e : values()) {
                    byCode.put(e.value, e);
                }
            '''.stripIndent(), className))
            .addMethod(MethodSpec.methodBuilder('getByCode')
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(className)
                .addParameter(TypeName.INT, 'code')
                .addStatement('return $L.get($L)', 'byCode', 'code')
                .build())
            .build()
    }

    static JavaEnum javaEnum(XTypeEnum xEnum) {
        String simpleName = getEnumJavaName(xEnum.name)
        Map<String, String> values = xEnum.items.collectEntries {
            [(getEnumValueName(it.name)):it.value.expression]
        }
        return new JavaEnum(
            basePackage: xEnum.basePackage,
            javaPackage: xEnum.javaPackage,
            simpleName: simpleName,
            className: getEnumClassName(xEnum.javaPackage, xEnum.name),
            superInterface: ClassName.get(xEnum.basePackage, 'IntValue'),
            values: values
        )
    }
}
