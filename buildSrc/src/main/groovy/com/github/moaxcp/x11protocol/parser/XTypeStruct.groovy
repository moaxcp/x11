package com.github.moaxcp.x11protocol.parser


import groovy.transform.ToString
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.parser.JavaListProperty.javaListProperty
import static com.github.moaxcp.x11protocol.parser.JavaObjectProperty.javaObjectProperty
import static com.github.moaxcp.x11protocol.parser.JavaStruct.javaStruct
import static XUnitField.getXField
import static XUnitListField.getXListField
import static XUnitPad.getXPad

@ToString(includeSuperProperties = true, includePackage = false, includes = ['name', 'type', 'protocol'])
class XTypeStruct extends XTypeResolved implements XTypeUnit {
    List<XUnit> protocol = []

    static XTypeStruct getXStruct(XResult result, Node node) {
        XTypeStruct struct = new XTypeStruct()
        struct.result = result
        struct.name = node.attributes().get('name')
        struct.type = node.name()
        node.childNodes().each { Node it ->
            switch(it.name()) {
                case 'field':
                    struct.protocol.add(getXField(result, it))
                    break
                case 'list':
                    struct.protocol.add(getXListField(result, it))
                    break
                case 'pad':
                    struct.protocol.add(getXPad(it))
                    break
                default:
                    throw new IllegalArgumentException("cannot parse ${it.name()}")
            }
        }

        return struct
    }

    @Override
    JavaType getJavaType() {
        return javaStruct(this)
    }

    @Override
    JavaProperty getJavaProperty(XUnitField field) {
        return javaObjectProperty(field)
    }

    @Override
    JavaListProperty getJavaListProperty(XUnitListField field) {
        return javaListProperty(field)
    }
}
