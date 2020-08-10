package com.github.moaxcp.x11protocol.parser.expression

import groovy.util.slurpersupport.Node

class ExpressionFactory {
    static Expression getExpression(String basePackage, Node node) {
        switch(node.name()) {
            case 'value':
                return new ValueExpression(value:node.text())
            case 'bit':
                return new BitValueExpression(Integer.valueOf(node.text()))
            case 'fieldref':
                return new FieldRefExpression(fieldName: node.text())
            case 'paramref':
                return new ParamRefExpression(paramName: node.text(), x11Primative: node.attributes().get('type'))
            case 'op':
                return getOpExpression(basePackage, node)
            case 'unop':
                return getUnopExpression(basePackage, node)
            case 'popcount':
                return getPopcountExpression(basePackage, node)
            default:
                throw new IllegalArgumentException("cannot parse ${node.name()}")
        }
    }

    static Expression getOpExpression(String basePackage, Node node) {
        List<Expression> expressions = node.childNodes().collect { Node it ->
            getExpression(basePackage, it)
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

    static Expression getUnopExpression(String basePackage, Node node) {
        List<Expression> expressions = node.childNodes().collect { Node it ->
            getExpression(basePackage, it)
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

    static Expression getPopcountExpression(String basePackage, Node node) {
        Node fieldNode = node.childNodes().next()
        FieldRefExpression field = new FieldRefExpression(fieldName: fieldNode.text())
        return new PopcountExpression(basePackage: basePackage, field: field)
    }
}
