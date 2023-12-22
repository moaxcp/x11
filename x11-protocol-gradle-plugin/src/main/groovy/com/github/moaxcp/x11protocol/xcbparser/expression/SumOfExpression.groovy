package com.github.moaxcp.x11protocol.xcbparser.expression

import com.github.moaxcp.x11protocol.xcbparser.JavaListProperty
import com.github.moaxcp.x11protocol.xcbparser.JavaProperty
import com.github.moaxcp.x11protocol.xcbparser.JavaType
import com.github.moaxcp.x11protocol.xcbparser.JavaTypeListProperty
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.convertX11VariableNameToJava
import static com.github.moaxcp.x11protocol.xcbparser.expression.Expressions.castOrder

class SumOfExpression implements Expression {
    JavaType javaType
    String referenceList
    Expression sumOf

    @Override
    List<FieldRefExpression> getFieldRefs() {
        return sumOf.getFieldRefs()
    }

    @Override
    List<ParamRefExpression> getParamRefs() {
        return sumOf.getParamRefs()
    }

    @Override
    TypeName getTypeName() {
        return TypeName.INT
    }

    @Override
    CodeBlock getExpression() {
        String listPropertyName = convertX11VariableNameToJava(referenceList)
        JavaListProperty listProperty = (JavaListProperty) javaType.getJavaProperty(listPropertyName)
        if(listProperty instanceof JavaTypeListProperty) {
            String typeField = fieldRefs.get(0).fieldName
            JavaProperty typeProp = listProperty.x11Field.result.resolveXType(listProperty.x11Field.type).javaType.getJavaProperty(typeField)
            CodeBlock code
            if(typeProp.typeName == TypeName.BYTE) {
                code = CodeBlock.of('Byte.toUnsignedInt(o.$L())', 'get' + typeField.capitalize())
            } else if(typeProp.typName == TypeName.SHORT) {
                code = CodeBlock.of('Short.toUnsignedInt(o.$L())', 'get' + typeField.capitalize())
            } else if(typeProp.typName == TypeName.INT) {
                code = CodeBlock.of('Integer.toUnsignedInt(o.$L())', 'get' + typeField.capitalize())
            } else {
                throw new IllegalStateException("not sure how to handle $typeField")
            }
            CodeBlock.Builder builder = CodeBlock.builder()
            builder.add('$L.stream().mapToInt(o -> $L).sum()', listProperty.name, code)
            return builder.build()
        }
        CodeBlock.Builder builder = CodeBlock.builder()
        builder.add('$L.stream().mapToInt(mapToInt -> mapToInt).sum()', listProperty.name)
        return builder.build()
    }

    @Override
    CodeBlock getExpression(TypeName primative) {
        if(castOrder(getTypeName()) > castOrder(primative)) {
            return CodeBlock.of('($T) ($L)', primative, expression)
        }
        return expression
    }
}
