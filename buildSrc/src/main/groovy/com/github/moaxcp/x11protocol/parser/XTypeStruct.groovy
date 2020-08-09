package com.github.moaxcp.x11protocol.parser

import groovy.transform.ToString
import groovy.util.slurpersupport.Node

import static XUnitField.xUnitField
import static XUnitListField.xUnitListField
import static JavaTypeListProperty.javaTypeListProperty
import static JavaTypeProperty.javaTypeProperty
import static com.github.moaxcp.x11protocol.parser.JavaStruct.javaStruct
import static com.github.moaxcp.x11protocol.parser.XUnitPadFactory.xUnitPad

@ToString(includeSuperProperties = true, includePackage = false, includes = ['name'])
class XTypeStruct extends XTypeResolved implements XTypeUnit {
    List<XUnit> protocol = []

    static XTypeStruct xStruct(XResult result, Node node) {
        XTypeStruct struct = new XTypeStruct(basePackage: result.basePackage, javaPackage: result.javaPackage)
        struct.name = node.attributes().get('name')
        node.childNodes().each { Node it ->
            switch(it.name()) {
                case 'field':
                    struct.protocol.add(xUnitField(result, it))
                    break
                case 'list':
                    struct.protocol.add(xUnitListField(result, it))
                    break
                case 'pad':
                    struct.protocol.add(xUnitPad(it))
                    break
                case 'switch':
                    System.out.println("switch on ${struct.name}")
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
    JavaTypeProperty getJavaProperty(XUnitField field) {
        return javaTypeProperty(field)
    }

    @Override
    JavaListProperty getJavaListProperty(XUnitListField field) {
        return javaTypeListProperty(field)
    }
}
