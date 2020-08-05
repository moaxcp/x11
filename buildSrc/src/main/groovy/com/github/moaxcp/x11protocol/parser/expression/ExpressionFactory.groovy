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
            case 'op':
                return getOpExpression(node)
            default:
                throw new IllegalArgumentException("cannot parse $node")
        }
    }

    static Expression getOpExpression(Node node) {
        switch(node.attributes().get('op')) {
            case '*':
                List<Expression> expressions = node.childNodes().collect { Node it ->
                    getExpression(it)
                }
                return new MultiplyExpression(expressions: expressions)
        }
    }
}
