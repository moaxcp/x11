package com.github.moaxcp.x11protocol.parser.expression
import groovy.util.slurpersupport.Node

class ExpressionFactory {
    static Expression getExpression(Node node) {
        switch(node.name()) {
            case 'value':
                return new ValueExpression(value:node.text())
            case 'bit':
                return new BitValueExpression(Integer.valueOf(node.text()))
            default:
                throw new IllegalArgumentException("cannot parse $node")
        }
    }
}
