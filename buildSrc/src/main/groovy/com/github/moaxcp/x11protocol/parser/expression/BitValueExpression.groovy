package com.github.moaxcp.x11protocol.parser.expression

class BitValueExpression extends ValueExpression {
    BitValueExpression(int bit) {
        if(bit > 32) {
            throw new IllegalArgumentException("bit $bit not supported")
        }
        StringBuilder builder = new StringBuilder('0b1')
        for(int i = 0; i < bit; i++) {
            builder.append('0')
        }
        value = builder.toString()
    }
}
