package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.*

class JavaEnumListProperty extends JavaListProperty {
    String x11Primative
    TypeName ioTypeName

    static JavaEnumListProperty javaEnumListProperty(XUnitListField field) {
        XTypeResolved resolvedType = field.resolvedType
        TypeName baseTypeName = getEnumTypeName(field.result.javaPackage, field.resolvedEnumType.name)
        TypeName typeName = ParameterizedTypeName.get(ClassName.get(List), baseTypeName)
        return new JavaEnumListProperty(
            name:convertX11VariableNameToJava(field.name),
            x11Primative:resolvedType.name,
            baseTypeName: baseTypeName,
            typeName: typeName,
            ioTypeName:x11PrimativeToJavaTypeName(resolvedType.name),
            lengthExpression: field.lengthExpression
        )
    }

    @Override
    CodeBlock getReadCode() {
        return CodeBlock.of('''\
            $1T $2L = new ArrayList<>($3L);
            for(int i = 0; i < $3L; i++) {
              $2L.add($4T.getByCode(in.read$5L()));
            }
        ''', typeName, name, lengthExpression.expression, baseTypeName, fromUpperToUpperCamel(x11Primative))
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.of('''\
            for($1T e : $2L) {
              out.write$3L(e.getValue());
            }
        ''', baseTypeName, name, fromUpperToUpperCamel(x11Primative))
    }
}
