package com.github.moaxcp.x11protocol.parser

import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.parser.JavaEvent.javaEvent

class XTypeEvent extends XTypeObject {
    int number

    XTypeEvent(Map map) {
        super(map)
        number = map.number ?: 0
        protocol.add(0, new XUnitField(result:this.result, name: 'NUMBER', type: 'CARD8', constantValue: number))
        protocol.add(1, new XUnitField(result: this.result, name:'event_detail', type: 'CARD8'))
        protocol.add(2, new XUnitField(result: this.result, name:'sequence_number', type: 'CARD16'))
    }

    static XTypeEvent xTypeEvent(XResult result, Node node) {
        int number = Integer.valueOf((String) node.attributes().get('number'))
        XTypeEvent event = new XTypeEvent(result: result, number: number, name: node.attributes().get('name'), basePackage: result.basePackage, javaPackage: result.javaPackage)
        event.addUnits(result, node)

        return event
    }
    
    static XTypeEvent xTypeEventCopy(XResult result, Node node) {
        int number = Integer.valueOf((String) node.attributes().get('number'))
        XTypeEvent event = new XTypeEvent(result: result, number: number, name: node.attributes().get('name'), basePackage: result.basePackage, javaPackage: result.javaPackage)
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
