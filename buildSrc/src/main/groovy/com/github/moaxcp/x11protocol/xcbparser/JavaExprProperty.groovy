package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.xcbparser.expression.Expression
import com.github.moaxcp.x11protocol.xcbparser.expression.Expressions
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import javax.lang.model.element.Modifier

import static com.github.moaxcp.x11protocol.generator.Conventions.fromUpperUnderscoreToUpperCamel

class JavaExprProperty extends JavaPrimativeProperty {
    final Expression expression
    JavaExprProperty(JavaType javaType, XUnitExprField field) {
        super(javaType, field)
        localOnly = true
        expression = Expressions.getExpression(javaType, field.expression)
    }

    @Override
    void addWriteCode(CodeBlock.Builder code) {
        code.addStatement('out.write$L($L)', fromUpperUnderscoreToUpperCamel(x11Type), "get${name.capitalize()}()")
    }

    @Override
    List<MethodSpec> getMethods() {
        MethodSpec getter = MethodSpec.methodBuilder("get${name.capitalize()}")
            .addModifiers(Modifier.PUBLIC)
            .returns(typeName)
            .addStatement('return ($L) > 0', expression.expression)
            .build()
        return [getter]
    }
}
