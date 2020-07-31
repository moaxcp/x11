package com.github.moaxcp.x11protocol.parser

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(excludes = 'result')
@EqualsAndHashCode
class XType {
    private XResult result
    private String type //(primative, struct, union, request)
    private String name //(CARD32, POINT, CreateWindow)

    XType(XResult result, String type, String name) {
        this.result = result
        this.type = type
        this.name = name
    }

    String getGroup() {
        result.header
    }

    String getType() {
        return type
    }

    String getName() {
        return name
    }
}
