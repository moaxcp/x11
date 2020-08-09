package com.github.moaxcp.x11protocol.parser.expression

import groovy.util.slurpersupport.Node

class ExpressionFactory {
    static Expression getExpression(Node node) {
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
                return getOpExpression(node)
            case 'unop':
                return getUnopExpression(node)
            default:
                throw new IllegalArgumentException("cannot parse ${node.name()}")
        }
    }

    static Expression getOpExpression(Node node) {
        List<Expression> expressions = node.childNodes().collect { Node it ->
            getExpression(it)
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

    static Expression getUnopExpression(Node node) {
        List<Expression> expressions = node.childNodes().collect { Node it ->
            getExpression(it)
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
}
