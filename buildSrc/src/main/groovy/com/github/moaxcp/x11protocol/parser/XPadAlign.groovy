package com.github.moaxcp.x11protocol.parser

class XPadAlign implements XUnit {
    int align
    XListField list

    @Override
    String getReadCode() {
        if(align == 4) {
            return "in.readPadAlign(${list.lengthExpression.expression});"
        }
        return "in.readPadAlign($align, ${list.lengthExpression.expression});"
    }

    @Override
    String getWriteCode() {
        if(align == 4) {
            return "out.writePadAlign(${list.lengthExpression.expression});"
        }
        return "out.writePadAlign($align, ${list.lengthExpression.expression});"
    }
}
