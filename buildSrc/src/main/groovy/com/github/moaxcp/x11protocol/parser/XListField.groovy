package com.github.moaxcp.x11protocol.parser

class XListField implements XUnit {
    XResult xResult
    String type
    String name
    Expression lengthExpression

    @Override
    String getReadCode() {
        return null
    }

    @Override
    String getWriteCode() {
        return null
    }
}
