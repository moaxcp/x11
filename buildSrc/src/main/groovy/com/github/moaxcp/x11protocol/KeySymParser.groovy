package com.github.moaxcp.x11protocol

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

class KeySymParser {
    String basePackage
    BufferedReader input
    Map<String, String> keysyms

    TypeSpec getTypeSpec() {
        boolean inComment = false
        String line = input.readLine()
        while(line != null) {
            if(line.startsWith('/*')) {
                inComment = true
            } else if(line.endsWith('*/')) {
                inComment = false
            }
            if(inComment) {
                continue
            }
            if(line.startsWith('#define')) {
                String[] parts = line.split('\\w+')
                keysyms.put(parts[1], parts[2])
            } else if(line.startsWith('#ifdef')) {
                //new group
            } else if(line.startsWith('#endif')) {
                //end of group
            }
        }

        TypeSpec.Builder builder = TypeSpec.enumBuilder(ClassName.get(basePackage, "KeySym"))
            .addModifiers(Modifier.PUBLIC)
            .addField(FieldSpec.builder(TypeName.INT, 'value', Modifier.PRIVATE).build())
            .addMethod(MethodSpec.constructorBuilder()
                .addParameter(TypeName.INT, 'value')
                .addStatement("this.\$N = \$N", 'value', 'value')
                .build())
        keysyms.entrySet().each {
            builder.addEnumConstant(it.key, TypeSpec.anonymousClassBuilder('$L', it.value).build()))
        }
        return builder.build()
    }
}
