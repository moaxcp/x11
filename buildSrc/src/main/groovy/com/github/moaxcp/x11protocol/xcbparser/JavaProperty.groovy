package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.*
import javax.lang.model.element.Modifier
import lombok.NonNull

import static com.github.moaxcp.x11protocol.generator.Conventions.convertX11VariableNameToJava
import static java.util.Objects.requireNonNull
/**
 * A JavaProperty represents a property within a JavaType.
 */
abstract class JavaProperty implements JavaUnit, JavaReadParameter {
    final JavaType javaType
    final XUnitField x11Field
    /**
     * the field is public static final in the java class
     */
    boolean constantField
    /**
     * not a field. used locally in read and write methods only
     */
    boolean localOnly
    /**
     * a parameter to the read method
     */
    boolean readParam
    /**
     * the type for the read parameter
     */
    TypeName readTypeName
    /**
     * write expression which wraps normal value in an expression. Should contain $L or $1L to take the normal expression.
     */
    CodeBlock writeValueExpression
    /**
     * expression used when assigning value to builder in read method
     */
    CodeBlock builderValueExpression
    /**
     * field is optional. must set the bitmask to use field
     */
    JavaBitcaseInfo bitcaseInfo

    JavaProperty(Map map) {
        javaType = requireNonNull(map.javaType, 'javaType must not be null')
        x11Field = requireNonNull(map.x11Field, 'field must not be null')
        constantField = map.constantField
        localOnly = map.localOnly
        if(map.x11Field.bitcaseInfo) {
            bitcaseInfo = new JavaBitcaseInfo(map.x11Field.result, map.x11Field.bitcaseInfo)
        }
    }
    
    JavaProperty(JavaType javaType, XUnitField field) {
        this.javaType = requireNonNull(javaType, 'javaType must not be null')
        this.x11Field = requireNonNull(field, 'field must not be null')
        this.localOnly = field.localOnly
        if(javaType.getXUnitSubtype().isPresent() && !field.caseInfo) {
            this.readParam = true
        }
        if(field.bitcaseInfo) {
            this.bitcaseInfo = new JavaBitcaseInfo(field.result, field.bitcaseInfo)
        }
    }

    @Override
    XUnitField getXUnit() {
        return x11Field
    }

    @Override
    String getName() {
        return convertX11VariableNameToJava(x11Field.name)
    }
    
    String getX11Type() {
        return x11Field.resolvedType.name
    }
    
    abstract TypeName getTypeName()
    
    boolean isNonNull() {
        if(bitcaseInfo) {
            return false
        }
        return true
    }

    @Override
    boolean isReadProtocol() {
        return !constantField && !readParam
    }

    @Override
    TypeName getReadTypeName() {
        if(readTypeName) {
            return readTypeName
        }
        return typeName
    }

    FieldSpec getMember() {
        if(localOnly) {
            throw new IllegalStateException("$name is a localOnly property")
        }
        if(constantField) {
            if(!x11Field.constantValue) {
                throw new IllegalStateException("$name missing constantValue in x11Field")
            }
            return FieldSpec.builder(typeName, name, Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL).initializer(constantValue).build()
        }
        FieldSpec.Builder builder = FieldSpec.builder(typeName, name)
            .addModifiers(Modifier.PRIVATE)
        if(nonNull) {
            builder.addAnnotation(NonNull)
        }
        return builder.build()
    }

    CodeBlock getConstantValue() {
        if(!constantField) {
            throw new IllegalStateException("not a constant value")
        }
        return CodeBlock.of(x11Field.constantValue)
    }

    @Override
    void addBuilderCode(CodeBlock.Builder code) {
        if(constantField || localOnly) {
            return
        }
        if(bitcaseInfo) {
            code.beginControlFlow('if(javaBuilder.is$LEnabled($T.$L))', bitcaseInfo.maskField.capitalize(), bitcaseInfo.enumType, bitcaseInfo.enumItem)
            code.addStatement('javaBuilder.$L($L)', name, readCode)
            code.endControlFlow()
        } else if(getBuilderValueExpression()) {
            code.addStatement('javaBuilder.$L($L)', name, getBuilderValueExpression())
        } else {
            code.addStatement('javaBuilder.$L($L)', name, name)
        }
    }

    CodeBlock getValueWriteExpressionCodeBlock() {
        if(writeValueExpression) {
            return writeValueExpression
        }
        return CodeBlock.of(name)
    }

    List<MethodSpec> getMethods() {
        return []
    }
    
    List<MethodSpec> getBuilderMethods(ClassName outer) {
        if(bitcaseInfo) {
            return [
                MethodSpec.methodBuilder(name)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(typeName, name)
                    .returns(javaType.builderClassName)
                    .addStatement('this.$1L = $1L', name)
                    .addStatement('$LEnable($T.$L)', bitcaseInfo.maskField, bitcaseInfo.enumType, bitcaseInfo.enumItem)
                    .addStatement('return this')
                    .build()
            ]
        }
        return []
    }

    CodeBlock declareAndInitializeTo(String readCall) {
        return CodeBlock.builder().addStatement('$T $L = $L', typeName, name, readCall).build()
    }

    CodeBlock declareAndInitializeTo(CodeBlock readCall) {
        return CodeBlock.builder().addStatement('$T $L = $L', typeName, name, readCall).build()
    }
}