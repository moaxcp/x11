package com.github.moaxcp.x11protocol

import com.squareup.javapoet.*
import javax.lang.model.element.Modifier

class KeySymParser {
    String basePackage
    BufferedReader input
    Map<String, String> keysyms = [:]

    ClassName getClassName() {
        ClassName.get(basePackage, "KeySym")
    }

    private String nextLine() {
        return input.readLine()?.trim()
    }

    TypeSpec getTypeSpec() {
        boolean inComment = false
        String line = nextLine()
        while(line != null) {
            if(line.startsWith('/*') && line.endsWith('*/')) {
                line = nextLine()
                continue
            } else if(line.startsWith('/*')) {
                inComment = true
            } else if(inComment && line.endsWith('*/')) {
                inComment = false
                line = nextLine()
                continue
            }
            if(inComment) {
                line = nextLine()
                continue
            }
            if(line.isEmpty()) {
                line = nextLine()
                continue
            }
            if(line.startsWith('#define')) {
                String[] parts = line.split('\\s+')
                keysyms.put(parts[1], parts[2])
            } else if(line.startsWith('#ifdef')) {
                //new group
            } else if(line.startsWith('#endif')) {
                //end of group
            }
            line = nextLine()
        }

        return TypeSpec.enumBuilder(className)
            .addModifiers(Modifier.PUBLIC)
            .addSuperinterface(ClassName.get(basePackage, 'IntValue'))
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
            .with(true) { TypeSpec.Builder builder ->
                keysyms.each {
                    builder.addEnumConstant(it.key, TypeSpec.anonymousClassBuilder('$L', it.value).build())
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
}
