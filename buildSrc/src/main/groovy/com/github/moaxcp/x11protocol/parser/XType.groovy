package com.github.moaxcp.x11protocol.parser

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.util.slurpersupport.Node

@ToString(excludes = 'result')
@EqualsAndHashCode(excludes = 'result')
class XType {
    XResult result
    String type //(primative, struct, union, request)
    String name //(CARD32, POINT, CreateWindow)

    static XType xidType(XResult result, Node node) {
        String name = node.attributes().get('name')
        return new XType(result:result, type:'xid', name:name)
    }

    static XType xidUnionType(XResult result, Node node) {
        String name = node.attributes().get('name')
        return new XType(result:result, type:'xidunion', name:name)
    }

    String getGroup() {
        result.header
    }
}
