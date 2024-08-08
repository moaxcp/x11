package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.xcbparser.expression.EmptyExpression
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import org.eclipse.collections.api.list.primitive.*

import static com.github.moaxcp.x11protocol.generator.Conventions.*

class JavaPrimativeListProperty extends JavaListProperty {

    JavaPrimativeListProperty(JavaType javaType, XUnitListField field) {
        super(javaType, field)
    }

    @Override
    ClassName getBaseTypeName() {
        return x11PrimativeToBoxedType(x11Field.resolvedType.name)
    }

    @Override
    TypeName getTypeName() {
        def x11Type = x11Field.resolvedType.name
        if(x11Type == 'void') {
            return ClassName.get(ByteList.class)
        }
        String primative = x11PrimativeToStorageTypeName(x11Type)
        switch(primative) {
            case 'boolean':
                return TypeName.get(BooleanList.class)
            case 'byte':
                return TypeName.get(ByteList.class)
            case 'short':
                return TypeName.get(ShortList.class)
            case 'char':
                return TypeName.get(CharList.class)
            case 'int':
                return TypeName.get(IntList.class)
            case 'long':
                return TypeName.get(LongList.class)
            case 'float':
                return TypeName.get(FloatList.class)
            case 'double':
                return TypeName.get(DoubleList.class)
            default:
                throw new IllegalArgumentException(primative)
        }
    }

    static JavaPrimativeListProperty javaPrimativeListProperty(JavaType javaType, XUnitListField field) {
        return new JavaPrimativeListProperty(javaType, field)
    }

    ClassName getEnumClassName() {
        if(x11Field.enumType) {
            XType resolvedEnumType = x11Field.resolvedEnumType
            return getEnumClassName(resolvedEnumType.javaPackage, resolvedEnumType.name)
        }
        return null
    }

    @Override
    List<MethodSpec> getBuilderMethods(ClassName outer) {
        List<MethodSpec> methods = super.getBuilderMethods(outer)
        if(enumClassName) {
            //todo add method which converts enums to type
        }
        return methods
    }

    @Override
    CodeBlock getReadCode() {
        if(lengthExpression instanceof EmptyExpression) {
            return CodeBlock.of('in.read$L($L)', fromUpperUnderscoreToUpperCamel(x11Type), CodeBlock.of('Short.toUnsignedInt(length) - javaStart'))
        } else {
            return CodeBlock.of('in.read$L($L)', fromUpperUnderscoreToUpperCamel(x11Type), lengthExpression.getExpression(TypeName.INT))
        }
    }

    @Override
    void addWriteCode(CodeBlock.Builder code) {
        code.addStatement("out.write${fromUpperUnderscoreToUpperCamel(x11Type)}($name)")
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
                actualSize = CodeBlock.of('1 * $L.size()', name)
                break
            case 'INT16':
            case 'CARD16':
                actualSize = CodeBlock.of('2 * $L.size()', name)
                break
            case 'INT32':
            case 'CARD32':
            case 'float':
            case 'fd':
                actualSize = CodeBlock.of('4 * $L.size()', name)
                break
            case 'CARD64':
            case 'double':
                actualSize = CodeBlock.of('8 * $L.size()', name)
                break
            default:
                throw new UnsupportedOperationException("type not supported $x11Type")
        }

        if(bitcaseInfo) {
            return CodeBlock.of('($L ? $L : 0)', bitcaseInfo.expression, actualSize)
        }
        return actualSize
    }

    @Override
    Optional<Integer> getFixedSize() {
        return Optional.empty()
    }
}
