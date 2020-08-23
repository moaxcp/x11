package com.github.moaxcp.x11protocol.parser

import groovy.util.slurpersupport.Node

class XUnitRequiredStartAlign implements XUnit {
    int align

    static XUnitRequiredStartAlign xUnitRequiredStartAlign(XResult result, Node node) {
        return new XUnitRequiredStartAlign(align: Integer.valueOf((String) node.attributes().get('align')))
    }

    @Override
    JavaRequiredStartAlign getJavaUnit(JavaType javaType) {
        return new JavaRequiredStartAlign(align:align)
    }
}
