package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.*
import javax.lang.model.element.Modifier
import lombok.Setter

import static com.github.moaxcp.x11protocol.generator.Conventions.convertX11VariableNameToJava
import static java.util.Objects.requireNonNull
/**
 * A JavaProperty represents a property within a JavaType.
 */
abstract class JavaProperty implements JavaUnit {
    final JavaType javaType
    final XUnitField x11Field
    
    boolean readOnly
    boolean localOnly

    JavaProperty(Map map) {
        javaType = map.javaType
        x11Field = map.x11Field
        readOnly = map.readOnly
        localOnly = map.localOnly
    }
    
    JavaProperty(JavaType javaType, XUnitField field) {
        this.javaType = requireNonNull(javaType, 'javaType must not be null')
        this.x11Field = requireNonNull(field, 'field must not be null')
    }
    
    XUnitField getXUnit() {
        return x11Field
    }
    
    String getName() {
        return convertX11VariableNameToJava(x11Field.name)
    }
    
    String getX11Type() {
        return x11Field.resolvedType.name
    }
    
    abstract TypeName getTypeName()

    FieldSpec getMember() {
        if(localOnly) {
            return null
        }
        FieldSpec.Builder builder = FieldSpec.builder(typeName, name)
            .addModifiers(Modifier.PRIVATE)
        if(readOnly) {
            builder.addAnnotation(
                AnnotationSpec.builder(Setter)
                    .addMember('value', CodeBlock.of('$T.PRIVATE', ClassName.get('lombok', 'AccessLevel')))
                    .build())
        }
        return builder.build()
    }

    String getSetterName() {
        if(localOnly) {
            return null
        }
        return "set${name.capitalize()}"
    }

    String getGetterName() {
        if(localOnly) {
            return null
        }
        return "get${name.capitalize()}"
    }

    List<MethodSpec> getMethods() {
        return []
    }

    CodeBlock declareAndInitializeTo(String readCall) {
        return CodeBlock.builder().addStatement('$T $L = $L', typeName, name, readCall).build()
    }

    CodeBlock declareAndInitializeTo(CodeBlock readCall) {
        return CodeBlock.builder().addStatement('$T $L = $L', typeName, name, readCall).build()
    }
}