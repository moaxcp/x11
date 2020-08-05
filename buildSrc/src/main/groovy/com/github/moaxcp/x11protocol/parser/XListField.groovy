package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.Expression
import com.github.moaxcp.x11protocol.parser.expression.ExpressionFactory
import com.squareup.javapoet.*
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.generator.Conventions.fromUpperToUpperCamel
import static com.github.moaxcp.x11protocol.generator.Conventions.getX11Primatives

class XListField extends ResolvableProperty {
    Expression lengthExpression

    static XListField getXListField(XResult result, Node node) {
        String fieldType = node.attributes().get('type')
        String fieldName = node.attributes().get('name')
        Expression expression = null
        if(node.childNodes().hasNext()) {
            expression = ExpressionFactory.getExpression((Node) node.childNodes().next())
        }
        return new XListField(result:result, type:fieldType, name:fieldName, lengthExpression: expression)
    }

    String getLengthField() {
        List<String> lengthFields = lengthExpression?.fieldRefs?.findAll {
            it.endsWith('_len')
        } ?: []
        if(lengthFields.size() > 1) {
            throw new IllegalStateException("multiple lengthFields for $name in $lengthFields")
        }

        return lengthFields[0]
    }

    @Override
    TypeName getJavaTypeName() {
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
    CodeBlock getReadCode() {
        XType type = resolvedType
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
}
