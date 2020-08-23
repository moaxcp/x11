package com.github.moaxcp.x11protocol.parser.expression

import com.github.moaxcp.x11protocol.parser.*
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import static java.util.Objects.requireNonNull

@ToString(includePackage = false)
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

    CodeBlock getExpression() {
        JavaProperty field = javaType.getField(fieldName)
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
            return CodeBlock.of(propertyName)
        } else {
            throw new UnsupportedOperationException("field not supported ${field.name}")
        }
    }
}
