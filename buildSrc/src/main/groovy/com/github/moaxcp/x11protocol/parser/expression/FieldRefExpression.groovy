package com.github.moaxcp.x11protocol.parser.expression

import com.github.moaxcp.x11protocol.parser.JavaListProperty
import com.github.moaxcp.x11protocol.parser.JavaPrimativeProperty
import com.github.moaxcp.x11protocol.parser.JavaProperty
import com.github.moaxcp.x11protocol.parser.JavaType
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName
import groovy.transform.EqualsAndHashCode

import static com.github.moaxcp.x11protocol.generator.Conventions.x11PrimativeToExpressionTypeName
import static com.github.moaxcp.x11protocol.parser.expression.Expressions.castOrder
import static java.util.Objects.requireNonNull

@EqualsAndHashCode
class FieldRefExpression implements Expression {
    String fieldName
    JavaType javaType

    @Override
    List<FieldRefExpression> getFieldRefs() {
        return [this]
    }

    @Override
    List<ParamRefExpression> getParamRefs() {
        return []
    }

    @Override
    TypeName getTypeName() {
        return x11PrimativeToExpressionTypeName(javaType.getJavaProperty(fieldName).x11Type)
    }

    CodeBlock getExpression() {
        JavaProperty field = javaType.getJavaProperty(fieldName)
        if(field == null && fieldName.endsWith('Len')) {
            String lengthField = fieldName.replace('Len', '')
            field = javaType.getJavaProperty(lengthField)
            if(field instanceof JavaListProperty && field.x11Type == 'CHAR2B') {
                return CodeBlock.of('$L.size()', lengthField)
            }
        }
        requireNonNull(field, "$fieldName in ${javaType.simpleName} was null")
        if(field instanceof JavaListProperty) {

        } else if(field instanceof JavaProperty) {
            if(!(field instanceof JavaPrimativeProperty)) {
                throw new UnsupportedOperationException("field ${field.name} is not primative")
            }
            String propertyName = field.name
            if(field.typeName == TypeName.BOOLEAN) {
                return CodeBlock.of("($propertyName ? 1 : 0)")
            }
            String x11Type = field.x11Type
            if(x11Type == 'CARD8') {
                return CodeBlock.of('Byte.toUnsignedInt($L)', propertyName)
            }
            if(x11Type == 'CARD16') {
                return CodeBlock.of('Short.toUnsignedInt($L)', propertyName)
            }
            if(x11Type == 'CARD32') {
                return CodeBlock.of('Integer.toUnsignedLong($L)', propertyName)
            }
            return CodeBlock.of(propertyName)
        } else {
            throw new UnsupportedOperationException("field not supported ${field.name}")
        }
    }

    @Override
    CodeBlock getExpression(TypeName primative) {
        if(castOrder(getTypeName()) > castOrder(primative)) {
            return CodeBlock.of('($T) ($L)', primative, expression)
        }
        return expression
    }
}
