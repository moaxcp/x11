package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.ClassName
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.generator.Conventions.getUnionJavaName
import static com.github.moaxcp.x11protocol.parser.JavaClientMessageDataUnionProperty.javaClientMessageDataUnionProperty
import static com.github.moaxcp.x11protocol.parser.JavaTypeProperty.javaTypeProperty
import static com.github.moaxcp.x11protocol.parser.JavaUnion.javaUnion
import static com.github.moaxcp.x11protocol.parser.XUnitField.xUnitField
import static com.github.moaxcp.x11protocol.parser.XUnitListField.xUnitListField
import static com.github.moaxcp.x11protocol.parser.XUnitPadFactory.xUnitPad

class XTypeUnion extends XTypeObject {
    static XTypeUnion xTypeUnion(XResult result, Node node) {
        XTypeUnion union = new XTypeUnion(basePackage: result.basePackage, javaPackage: result.javaPackage)
        union.name = node.attributes().get('name')
        node.childNodes().each { Node it ->
            switch(it.name()) {
                case 'field':
                    XUnitField field = xUnitField(result, it)
                    XType resolved = field.resolvedType
                    if(resolved instanceof XTypeStruct) {
                        resolved.superTypes += ClassName.get(union.javaPackage, getUnionJavaName(union.name))
                    }
                    union.protocol.add(field)
                    break
                case 'list':
                    union.protocol.add(xUnitListField(result, it))
                    break
                case 'pad':
                    union.protocol.add(xUnitPad(it))
                    break
                case 'switch':
                    System.out.println("switch on ${union.name}")
                    break
                default:
                    throw new IllegalArgumentException("cannot parse ${it.name()}")
            }
        }

        return union
    }

    @Override
    JavaType getJavaType() {
        return javaUnion(this)
    }
    
    @Override
    JavaTypeProperty getJavaProperty(JavaType javaType, XUnitField field) {
        if(name == 'ClientMessageData') {
            return javaClientMessageDataUnionProperty(field)
        }
        return javaTypeProperty(field)
    }
}
