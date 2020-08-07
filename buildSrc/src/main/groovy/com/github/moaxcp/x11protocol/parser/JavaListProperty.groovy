package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.Expression
import com.squareup.javapoet.*

import static com.github.moaxcp.x11protocol.generator.Conventions.*

class JavaListProperty extends JavaProperty {
    String name
    String x11Primative
    TypeName typeName
    Expression lengthExpression
    boolean readOnly
    boolean localOnly

    static JavaListProperty javaListProperty(XUnitListField field) {
        XTypeResolved resolvedType = field.resolvedType
        return new JavaListProperty(
            name:convertX11VariableNameToJava(field.name),
            x11Primative: resolvedType.name
        )
    }

    @Override
    TypeName getTypeName() {
        TypeName baseType = super.getJavaTypeName()
        if(baseType == TypeName.CHAR) {
            return ClassName.get(String)
        } else if(baseType.isPrimitive()) {
            return ArrayTypeName.of(baseType)
        } else {
            return ParameterizedTypeName.get(ClassName.get(List), baseType)
        }
    }

    @Override
    FieldSpec getMember() {
        return null
    }

    @Override
    CodeBlock getReadCode() {
        XTypeResolved type = resolvedType
        switch(type.type) {
            case 'primative':
                if(x11Primatives.contains(type.name)) {
                    return declareAndInitializeTo("in.read${fromUpperToUpperCamel(type.name)}(${lengthExpression.expression})")
                }
                throw new IllegalArgumentException("primative ${type.name} from $type not supported")
                break
            case 'enum':
                return CodeBlock.of("\$1T \$2L = \$1T.getByCode(in.read${fromUpperToUpperCamel(originalResolvedType.name)}())",
                    javaTypeName, javaName)
                break
            case 'xid':
            case 'xidunion':
                return declareAndInitializeTo("in.readCard32()")
                break
            default:
                throw new IllegalArgumentException("type not supported $type")
        }
    }

    @Override
    CodeBlock getWriteCode() {
        return null
    }
}
