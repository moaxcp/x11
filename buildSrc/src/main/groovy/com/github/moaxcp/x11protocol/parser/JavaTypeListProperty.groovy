package com.github.moaxcp.x11protocol.parser


import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName

import static com.github.moaxcp.x11protocol.generator.Conventions.getStructTypeName

class JavaTypeListProperty extends JavaListProperty {

    JavaTypeListProperty(JavaType javaType, XUnitListField field) {
        super(javaType, field)
    }

    static JavaTypeListProperty javaTypeListProperty(JavaType javaType, XUnitListField field) {
        return new JavaTypeListProperty(javaType, field)
    }

    String getBasePackage() {
        return javaType.basePackage
    }

    @Override
    ClassName getBaseTypeName() {
        XType type = x11Field.resolvedType
        if(type instanceof XTypeStruct) {
            return getStructTypeName(type.javaPackage, type.name)
        } else { //else Request/Reply/Event
            throw new UnsupportedOperationException("not supported ${type.name}")
        }
    }

    @Override
    TypeName getTypeName() {
        return ParameterizedTypeName.get(ClassName.get(List), baseTypeName)
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
    CodeBlock getSize() {
        return CodeBlock.of('$T.sizeOf($L)', ClassName.get(basePackage, 'XObject'), name)
    }
}
