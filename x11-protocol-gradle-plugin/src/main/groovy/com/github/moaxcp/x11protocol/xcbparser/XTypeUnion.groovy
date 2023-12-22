package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.ClassName
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.generator.Conventions.getUnionJavaName
import static com.github.moaxcp.x11protocol.xcbparser.JavaClientMessageDataUnionProperty.javaClientMessageDataUnionProperty
import static com.github.moaxcp.x11protocol.xcbparser.JavaNotifyDataUnionProperty.javaNotifyDataUnionProperty
import static com.github.moaxcp.x11protocol.xcbparser.JavaTypeProperty.javaTypeProperty
import static com.github.moaxcp.x11protocol.xcbparser.JavaUnion.javaUnion
import static com.github.moaxcp.x11protocol.xcbparser.XUnitField.xUnitField
import static com.github.moaxcp.x11protocol.xcbparser.XUnitListField.xUnitListField
import static com.github.moaxcp.x11protocol.xcbparser.XUnitPadFactory.xUnitPad

class XTypeUnion extends XTypeObject {

    XTypeUnion(Map map) {
        super(map)
    }

    static XTypeUnion xTypeUnion(XResult result, Node node) {
        XTypeUnion union = new XTypeUnion(result: result, name: node.attributes().get('name'), basePackage: result.basePackage, javaPackage: result.javaPackage)
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
                    throw new UnsupportedOperationException("switch on union")
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
    JavaType getSubType(String subType) {
        return javaUnion(this, subType)
    }

    @Override
    JavaTypeProperty getJavaProperty(JavaType javaType, XUnitField field) {
        if(name == 'ClientMessageData') {
            return javaClientMessageDataUnionProperty(javaType, field)
        } else if(name == 'NotifyData') {
            return javaNotifyDataUnionProperty(javaType, field)
        }
        return javaTypeProperty(javaType, field)
    }
}
