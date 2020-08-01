package com.github.moaxcp.x11protocol.parser

class XPadAlign implements XUnit {
    int align
    XListField list

    @Override
    String getReadCode() {
        if(align == 4) {
            return "in.readPad(${list.lengthExpression});"
        }
        return "in.readPad($align, ${list.lengthExpression});"
    }

    @Override
    String getWriteCode() {
        return null
    }
}
