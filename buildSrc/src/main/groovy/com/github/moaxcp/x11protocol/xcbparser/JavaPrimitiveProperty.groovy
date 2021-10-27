package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.*
import javax.lang.model.element.Modifier
import lombok.NonNull

import static com.github.moaxcp.x11protocol.generator.Conventions.*

/**
 * for x11 primative properties
 */
class JavaPrimitiveProperty extends JavaProperty {
    String lengthOfField

    JavaPrimitiveProperty(Map map) {
        super(map)
        lengthOfField = map.lengthOfField
    }

    JavaPrimitiveProperty(JavaClass javaClass, XUnitField field) {
        super(javaClass, field)
    }

    @Override
    String getName() {
        if(typeName == TypeName.BOOLEAN) {
            if(x11Field.name.startsWith('is_')) {
                return convertX11VariableNameToJava(x11Field.name.replace('is_', ''))
            }
        }
        return convertX11VariableNameToJava(x11Field.name)
    }

    @Override
    TypeName getTypeName() {
        return memberTypeName
    }

    @Override
    CodeBlock getDefaultValue() {
        if(typeName == TypeName.BOOLEAN) {
            return CodeBlock.of('false')
        } else {
            return CodeBlock.of('0')
        }
    }

    TypeName getMemberTypeName() {
        if(!x11Primatives.contains(x11Field.resolvedType.name)) {
            throw new IllegalStateException("Could not find ${x11Field.resolvedType.name} in primative types $x11Primatives")
        }
        return x11PrimativeToStorageTypeName(x11Field.resolvedType.name)
    }
    
    @Override
    boolean isNonNull() {
        return false
    }

    ClassName getEnumClassName() {
        if(x11Field.enumType) {
            XType resolvedEnumType = x11Field.resolvedEnumType
            return getEnumClassName(resolvedEnumType.javaPackage, resolvedEnumType.name)
        }
        return null
    }

    TypeName getMaskTypeName() {
        if(x11Field.maskType) {
            XType resolvedMaskType = x11Field.resolvedMaskType
            return getEnumClassName(resolvedMaskType.javaPackage, resolvedMaskType.name)
        }
        return null
    }

    @Override
    CodeBlock getConstantValue() {
        if(!constantField) {
            throw new IllegalStateException("not a constant value")
        }
        if(typeName == TypeName.BYTE && Integer.valueOf(x11Field.constantValue) > 127) {
            return CodeBlock.of('(byte) $L', x11Field.constantValue)
        }
        return CodeBlock.of(x11Field.constantValue)
    }

    @Override
    List<MethodSpec> getMethods() {
        List<MethodSpec> methods = super.getMethods()
        if(maskTypeName && !localOnly) {
            methods += [
                MethodSpec.methodBuilder("is${name.capitalize()}Enabled")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(boolean.class)
                    .addParameter(ParameterSpec.builder(ArrayTypeName.of(maskTypeName), 'maskEnums').addAnnotation(NonNull.class).build())
                    .varargs()
                    .beginControlFlow('for($T m : maskEnums)', maskTypeName)
                    .beginControlFlow('if(!m.isEnabled($L))', name)
                    .addStatement('return false')
                    .endControlFlow()
                    .endControlFlow()
                    .addStatement('return true')
                    .build()
            ]
        }
        return methods
    }

    @Override
    List<MethodSpec> getBuilderMethods(ClassName outer) {
        List<MethodSpec> methods = super.getBuilderMethods(outer)
        if(maskTypeName && !localOnly) {
            Modifier modifier = Modifier.PUBLIC
            if(name == 'valueMask') {
                modifier = Modifier.PRIVATE
                methods += MethodSpec.methodBuilder(name)
                    .addModifiers(Modifier.PRIVATE)
                    .returns(javaClass.builderClassName)
                    .addParameter(typeName, name)
                    .addStatement('this.$1L = $1L', name)
                    .addStatement('return this')
                    .build()
            }
            methods += [
                MethodSpec.methodBuilder("is${name.capitalize()}Enabled")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(boolean.class)
                    .addParameter(ParameterSpec.builder(ArrayTypeName.of(maskTypeName), 'maskEnums').addAnnotation(NonNull.class).build())
                    .varargs()
                    .beginControlFlow('for($T m : maskEnums)', maskTypeName)
                    .beginControlFlow('if(!m.isEnabled($L))', name)
                    .addStatement('return false')
                    .endControlFlow()
                    .endControlFlow()
                    .addStatement('return true')
                    .build(),
                MethodSpec.methodBuilder("${name}Enable")
                    .addModifiers(modifier)
                    .addParameter(ArrayTypeName.of(maskTypeName), 'maskEnums')
                    .varargs()
                    .returns(javaClass.builderClassName)
                    .beginControlFlow('for($T m : maskEnums)', maskTypeName)
                    .addStatement('$1L(($2T) m.enableFor($1L))', name, memberTypeName)
                    .endControlFlow()
                    .addStatement('return this')
                    .build(),
                MethodSpec.methodBuilder("${name}Disable")
                    .addModifiers(modifier)
                    .addParameter(ArrayTypeName.of(maskTypeName), 'maskEnums')
                    .varargs()
                    .returns(javaClass.builderClassName)
                    .beginControlFlow('for($T m : maskEnums)', maskTypeName)
                    .addStatement('$1L(($2T) m.disableFor($1L))', name, memberTypeName)
                    .endControlFlow()
                    .addStatement('return this')
                    .build()
            ]
        }
        if(enumClassName) {
            CodeBlock conversion
            if(memberTypeName == TypeName.BOOLEAN) {
                conversion = CodeBlock.of('$L.getValue() > 0', name)
            } else {
                conversion = CodeBlock.of('($2T) $1L.getValue()', name, memberTypeName)
            }
            if(bitcaseInfo) {
                String fieldName = bitcaseInfo.getMaskField().fieldRefs[0].fieldName
                methods += [
                    MethodSpec.methodBuilder(name)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(enumClassName, name)
                        .returns(javaClass.builderClassName)
                        .addStatement('this.$L = $L', name, conversion)
                        .addStatement('$LEnable($T.$L)', fieldName, bitcaseInfo.enumType, bitcaseInfo.enumItem)
                        .addStatement('return this')
                        .build()
                ]
            } else {
                methods += [
                    MethodSpec.methodBuilder(name)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(enumClassName, name)
                        .returns(javaClass.builderClassName)
                        .addStatement('this.$L = $L', name, conversion)
                        .addStatement('return this')
                        .build(),
                    MethodSpec.methodBuilder(name)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(typeName, name)
                        .returns(javaClass.builderClassName)
                        .addStatement('this.$1L = $1L', name)
                        .addStatement('return this')
                        .build()
                ]
            }
        }
        return methods
    }

    @Override
    CodeBlock getReadCode() {
        return CodeBlock.of("in.read${fromUpperUnderscoreToUpperCamel(x11Type)}()")
    }

    @Override
    void addWriteCode(CodeBlock.Builder code) {
        if(lengthOfField) {
            if(memberTypeName != TypeName.INT) {
                code.addStatement('$1T $2L = ($1T) $3L.size()', memberTypeName, name, lengthOfField)
            } else {
                code.addStatement('$1T $2L = $3L.size()', memberTypeName, name, lengthOfField)
            }
        }
        code.addStatement('out.write$L($L)', fromUpperUnderscoreToUpperCamel(x11Type), getValueWriteExpressionCodeBlock())
    }

    @Override
    CodeBlock getBuilderValueExpression() {
        if(super.builderValueExpression) {
            return super.builderValueExpression
        }
        if(typeName == TypeName.BOOLEAN && readTypeName in [TypeName.BYTE, TypeName.CHAR, TypeName.SHORT, TypeName.INT, TypeName.LONG, TypeName.FLOAT, TypeName.DOUBLE]) {
            return CodeBlock.of('$L > 0', name)
        }
        return null
    }

    @Override
    CodeBlock getSizeExpression() {
        CodeBlock actualSize
        switch(x11Type) {
            case 'BOOL':
            case 'byte':
            case 'BYTE':
            case 'INT8':
            case 'CARD8':
            case 'char':
            case 'void':
                actualSize = CodeBlock.of('1')
                break
            case 'INT16':
            case 'CARD16':
                actualSize = CodeBlock.of('2')
                break
            case 'INT32':
            case 'CARD32':
            case 'float':
            case 'fd':
                actualSize = CodeBlock.of('4')
                break
            case 'CARD64':
            case 'double':
                actualSize = CodeBlock.of('8')
                break
            default:
                throw new UnsupportedOperationException("type not supported $x11Type")
        }

        if(bitcaseInfo) {
            return CodeBlock.of('($T.$L.isEnabled($L) ? $L : 0)', bitcaseInfo.enumType, bitcaseInfo.enumItem, bitcaseInfo.maskField.getExpression(TypeName.INT), actualSize)
        }
        return actualSize
    }

    @Override
    Optional<Integer> getFixedSize() {
        if(bitcaseInfo) {
            return Optional.empty()
        }
        switch(x11Type) {
            case 'BOOL':
            case 'byte':
            case 'BYTE':
            case 'INT8':
            case 'CARD8':
            case 'char':
            case 'void':
                return Optional.of(1)
            case 'INT16':
            case 'CARD16':
                return Optional.of(2)
            case 'INT32':
            case 'CARD32':
            case 'float':
            case 'fd':
                return Optional.of(4)
            case 'CARD64':
            case 'double':
                return Optional.of(8)
        }
        throw new UnsupportedOperationException("type not supported $x11Type")
    }
}
