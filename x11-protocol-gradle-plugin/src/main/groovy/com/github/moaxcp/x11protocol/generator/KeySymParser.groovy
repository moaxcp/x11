package com.github.moaxcp.x11protocol.generator


import com.squareup.javapoet.*

import javax.lang.model.element.Modifier

class KeySymParser {
    KeySymResult result
    String corePackage

    KeySymParser(String corePackage, String keysymPackage) {
        this.corePackage = corePackage
        result = new KeySymResult(keysymPackage: keysymPackage)
    }

    KeySymResult merge(BufferedReader input) {
        boolean inComment = false
        String line = nextLine(input)
        while(line != null) {
            if(line.startsWith('/*') && line.endsWith('*/')) {
                line = nextLine(input)
                continue
            } else if(line.startsWith('/*')) {
                inComment = true
            } else if(inComment && line.endsWith('*/')) {
                inComment = false
                line = nextLine(input)
                continue
            }
            if(inComment) {
                line = nextLine(input)
                continue
            }
            if(line.isEmpty()) {
                line = nextLine(input)
                continue
            }
            if(line.startsWith('#define')) {
                String[] parts = line.split('\\s+')
                if(parts.length >= 3) {
                    result.keysyms.put(parts[1], parts[2])
                }
            } else if(line.startsWith('#ifdef')) {
                //new group
            } else if(line.startsWith('#endif')) {
                //end of group
            }
            line = nextLine(input)
        }
    }

    private static String nextLine(BufferedReader input) {
        return input.readLine()?.trim()
    }

    TypeSpec getTypeSpec() {
        return TypeSpec.enumBuilder(result.className)
            .addAnnotation(AnnotationSpec.builder(SuppressWarnings).addMember('value', '"java:S115"').build())
            .addModifiers(Modifier.PUBLIC)
            .addSuperinterface(ClassName.get(corePackage, 'IntValue'))
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
                result.keysyms.each {
                    builder.addEnumConstant(it.key, TypeSpec.anonymousClassBuilder('$L', it.value).build())
                }
                builder
            }
            .addField(FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(Integer), result.className), 'byCode')
                .addModifiers(Modifier.STATIC, Modifier.FINAL)
                .initializer('new $T<>()', ClassName.get(HashMap))
                .build())
            .addStaticBlock(CodeBlock.of('''\
                for($T e : values()) {
                    byCode.put(e.value, e);
                }
            '''.stripIndent(), result.className))
            .addMethod(MethodSpec.methodBuilder('getByCode')
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(result.className)
                .addParameter(TypeName.INT, 'code')
                .addStatement('return $L.get($L)', 'byCode', 'code')
                .build())
            .build()
    }
}
