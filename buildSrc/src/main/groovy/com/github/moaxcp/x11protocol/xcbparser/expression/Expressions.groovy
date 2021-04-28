package com.github.moaxcp.x11protocol.xcbparser.expression

import com.github.moaxcp.x11protocol.xcbparser.JavaType
import com.squareup.javapoet.TypeName
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.generator.Conventions.convertX11VariableNameToJava

class Expressions {
    static TypeName selectCastUp(TypeName first, TypeName second) {
        int order1 = castOrder(first)
        int order2 = castOrder(second)
        if(order1 > order2) {
            return first
        }
        return second
    }

    static int castOrder(TypeName name) {
        switch(name) {
            case TypeName.BOOLEAN:
            case TypeName.BYTE:
                return 1
            case TypeName.SHORT:
                return 2
            case TypeName.INT:
                return 3
            case TypeName.LONG:
                return 4
            default:
                throw new IllegalArgumentException('invalid type ' + name)
        }
    }

    static Expression getExpression(JavaType javaType, Node node) {
        if(!node) {
            return new EmptyExpression()
        }
        switch(node.name()) {
            case 'value':
                return new ValueExpression(value:node.text())
            case 'bit':
                return new BitValueExpression(Integer.valueOf(node.text()))
            case 'fieldref':
                return new FieldRefExpression(fieldName: convertX11VariableNameToJava((String) node.text()), javaType: javaType)
            case 'paramref':
                return new ParamRefExpression(paramName: convertX11VariableNameToJava(node.text()), x11Type: node.attributes().get('type'))
            case 'op':
                return getOpExpression(javaType, node)
            case 'unop':
                return getUnopExpression(javaType, node)
            case 'popcount':
                return getPopcountExpression(javaType, node)
            case 'sumof':
                return getSumofExpression(javaType, node)
            default:
                throw new IllegalArgumentException("cannot parse ${node.name()}")
        }
    }

    static Expression getOpExpression(JavaType javaType, Node node) {
        List<Expression> expressions = node.childNodes().collect { Node it ->
            getExpression(javaType, it)
        }
        switch(node.attributes().get('op')) {
            case '&':
                return new AndExpression(expressions: expressions)
            case '*':
                return new MultiplyExpression(expressions: expressions)
            case '/':
                return new DivideExpression(expressions: expressions)
            case '+':
                return new AddExpression(expressions: expressions)
            case '-':
                return new SubtractExpression(expressions: expressions)
            default:
                throw new IllegalArgumentException("unsupported op ${node.attributes().get('op')}")
        }
    }

    static Expression getUnopExpression(JavaType javaType, Node node) {
        List<Expression> expressions = node.childNodes().collect { Node it ->
            getExpression(javaType, it)
        }
        if(expressions.size() > 1) {
            throw new IllegalArgumentException("multiple expressions for urnary operator")
        }
        switch(node.attributes().get('op')) {
            case '~':
                return new NotExpression(unExpression: expressions.get(0))
            default:
                throw new IllegalArgumentException("unsupported op ${node.attributes().get('op')}")
        }
    }

    static Expression getPopcountExpression(JavaType javaType, Node node) {
        Node fieldNode = node.childNodes().next()
        FieldRefExpression field = new FieldRefExpression(javaType: javaType, fieldName: fieldNode.text())
        return new PopcountExpression(field: field)
    }

    static Expression getSumofExpression(JavaType javaType, Node node) {
        String ref = node.attributes().get('ref')
        Expression expression = getExpression(javaType, node.childNodes().next())
        return new SumOfExpression(javaType: javaType, referenceList: ref, sumOf: expression)
    }
}
