package com.github.moaxcp.x11protocol.parser

import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.parser.JavaEvent.javaEvent

class XTypeEvent extends XTypeObject {
    int number

    static XTypeEvent xTypeEvent(XResult result, Node node) {
        XTypeEvent event = new XTypeEvent(result: result, basePackage: result.basePackage, javaPackage: result.javaPackage)
        event.name = node.attributes().get('name')
        event.number = Integer.valueOf((String) node.attributes().get('number'))
        event.addUnits(result, node)

        return event
    }
    
    static XTypeEvent xTypeEventCopy(XResult result, Node node) {
        XTypeEvent event = new XTypeEvent(result: result, basePackage: result.basePackage, javaPackage: result.javaPackage)
        event.name = node.attributes().get('name')
        event.number = Integer.valueOf((String) node.attributes().get('number'))
        XTypeEvent ref = result.resolveXType((String) node.attributes().get('ref'))
        event.superTypes = ref.superTypes
        event.protocol = ref.protocol
        
        return event
    }

    @Override
    JavaType getJavaType() {
        return javaEvent(this)
    }
}
