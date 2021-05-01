package com.github.moaxcp.x11protocol.xcbparser

import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.xcbparser.JavaEvent.javaEvent

class XTypeEvent extends XTypeObject {
    int number
    boolean genericEvent
    int genericEventNumber

    XTypeEvent(Map map) {
        super(map)
        number = map.number ?: 0
        genericEvent = map.genericEvent ?: false
        genericEventNumber = map.genericEventNumber ?: -1
    }

    static XTypeEvent xTypeEvent(XResult result, Node node) {
        int number = Integer.valueOf((String) node.attributes().get('number'))
        XTypeEvent event = new XTypeEvent(result: result, number: number, name: node.attributes().get('name'), genericEvent: node.attributes().get('xge'), basePackage: result.basePackage, javaPackage: result.javaPackage)
        event.addUnits(result, node)
        if(event.genericEvent) {
            event.number = 35
            event.genericEventNumber = number
            event.protocol.add(0, new XUnitField(result: result, name: 'NUMBER', type: 'CARD8', constantValue: 35))
            event.protocol.add(1, new XUnitField(result: result, name: 'extension', type: 'CARD8'))
            event.protocol.add(2, new XUnitField(result: result, name: 'sequence_number', type: 'CARD16'))
            event.protocol.add(3, new XUnitField(result: result, name: 'length', type: 'CARD32', localOnly: true))
            event.protocol.add(4, new XUnitField(result: result, name: 'event_type', type: 'CARD16', constantValue: number))

        } else {
            event.protocol.add(0, new XUnitField(result: result, name: 'NUMBER', type: 'CARD8', constantValue: number))
            event.protocol.add(2, new XUnitField(result: result, name: 'sequence_number', type: 'CARD16'))
        }

        return event
    }
    
    static XTypeEvent xTypeEventCopy(XResult result, Node node) {
        int number = Integer.valueOf((String) node.attributes().get('number'))
        XTypeEvent ref = result.resolveXType((String) node.attributes().get('ref'))
        if(ref.genericEvent) {
            number = 35
        }
        XTypeEvent event = new XTypeEvent(result: result, number: number, name: node.attributes().get('name'), basePackage: result.basePackage, javaPackage: result.javaPackage)
        event.superTypes = ref.superTypes
        event.protocol = ref.protocol
        if(ref.genericEvent) {
            event.genericEvent = ref.genericEvent
            event.genericEventNumber = ref.genericEventNumber
        }
        
        return event
    }

    @Override
    JavaType getJavaType() {
        return javaEvent(this)
    }
}
