package com.github.moaxcp.x11protocol.parser.expression


import com.squareup.javapoet.CodeBlock
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import static com.github.moaxcp.x11protocol.generator.Conventions.convertX11VariableNameToJava

@ToString(includePackage = false)
@EqualsAndHashCode
class FieldRefExpression implements Expression {
    String fieldName
    String x11Type

    @Override
    List<FieldRefExpression> getFieldRefs() {
        return [this]
    }

    @Override
    List<ParamRefExpression> getParamRefs() {
        return []
    }

    CodeBlock getExpression() {
        if(x11Type == 'BOOL') {
            return CodeBlock.of("(${convertX11VariableNameToJava(fieldName)} ? 1 : 0)")
        }
        return CodeBlock.of(convertX11VariableNameToJava(fieldName))
    }
}
