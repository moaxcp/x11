package com.github.moaxcp.x11protocol.parser.expression

import com.github.moaxcp.x11protocol.parser.JavaType
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.generator.Conventions.convertX11VariableNameToJava

class ExpressionFactory {
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
}
