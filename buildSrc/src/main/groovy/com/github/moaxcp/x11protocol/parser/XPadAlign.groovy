package com.github.moaxcp.x11protocol.parser

class XPadAlign implements XUnit {
    private int align
    private XListField list

    XPadAlign(int align, XListField list) {
        this.align = align
        this.list = list
    }

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
