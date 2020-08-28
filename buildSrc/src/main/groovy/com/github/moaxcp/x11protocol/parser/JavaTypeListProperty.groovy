package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.ExpressionFactory
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.convertX11VariableNameToJava
import static com.github.moaxcp.x11protocol.generator.Conventions.getStructTypeName

class JavaTypeListProperty extends JavaListProperty {

    static JavaTypeListProperty javaTypeListProperty(JavaType javaType, XUnitListField field) {
        XType resolvedType = field.resolvedType
        TypeName baseTypeName
        if(resolvedType instanceof XTypeStruct) {
            baseTypeName = getStructTypeName(resolvedType.javaPackage, resolvedType.name)
        } else { //else Request/Reply/Event
            throw new UnsupportedOperationException("not supported $resolvedType")
        }
        TypeName typeName = ParameterizedTypeName.get(ClassName.get(List), baseTypeName)
        return new JavaTypeListProperty(
            name:convertX11VariableNameToJava(field.name),
            baseTypeName: baseTypeName,
            typeName: typeName,
            lengthExpression: ExpressionFactory.getExpression(javaType, field.lengthExpression)
        )
    }

    @Override
    CodeBlock getReadCode() {
        return CodeBlock.of('''\
            $1T $2L = new $3T<>();
            for(int i = 0; i < $4L; i++) {
              $2L.add($5L.read$5L(in));
            }
        '''.stripIndent(), typeName, name, ClassName.get('java.util', 'ArrayList'), lengthExpression.expression, baseTypeName.simpleName())
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.of('''\
            for($1T t : $2L) {
              t.write(out);
            }
        '''.stripIndent(), baseTypeName, name)
    }

    @Override
    int getSize() {
        return 0
    }
}
