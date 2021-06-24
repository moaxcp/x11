package com.github.moaxcp.x11protocol.xcbparser


import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.xcbparser.JavaEventStruct.javaEventStruct
import static com.github.moaxcp.x11protocol.xcbparser.JavaTypeListProperty.javaTypeListProperty
import static com.github.moaxcp.x11protocol.xcbparser.JavaTypeProperty.javaTypeProperty

class XTypeEventStruct extends XType implements XTypeUnit {

    String name
    //TODO the following properties may be used for verification of the event property when adding it to another xobject
    String allowedExtension
    boolean allowGenericEvents
    int minOpcode
    int maxOpcode

    static XTypeEventStruct xTypeEventStruct(XResult result, Node node) {
        String name = node.attributes().get("name")
        Node allowed = node.childNodes().find { Node n -> n.name() == 'allowed'}
        String allowedExtension = allowed.attributes().get('extension')
        boolean allowGenericEvents = allowed.attributes().get('xge')
        int minOpcode = Integer.valueOf(allowed.attributes().get('opcode-min'))
        int maxOpcode = Integer.valueOf(allowed.attributes().get('opcode-max'))
        XTypeEventStruct struct = new XTypeEventStruct(name: name, allowedExtension: allowedExtension,
                allowGenericEvents: allowGenericEvents, minOpcode: minOpcode, maxOpcode: maxOpcode, result: result,
                basePackage: result.basePackage, javaPackage: result.javaPackage)
        return struct
    }

    XTypeEventStruct(Map map) {
        super(map)
    }

    @Override
    List<String> getSubTypeNames() {
        return []
    }

    @Override
    JavaType getJavaType() {
        return javaEventStruct(this)
    }

    @Override
    boolean hasSubTypes() {
        return false
    }

    @Override
    JavaType getSubType(String subType) {
        throw new UnsupportedOperationException("event struct does not support sub types")
    }

    @Override
    List<JavaType> getSubTypes() {
        throw new UnsupportedOperationException("event struct does not support subtypes")
    }

    @Override
    JavaProperty getJavaProperty(JavaType javaType, XUnitField field) {
        return javaTypeProperty(javaType, field)
    }

    @Override
    JavaListProperty getJavaProperty(JavaType javaType, XUnitListField field) {
        return javaTypeListProperty(javaType, field)
    }
}
