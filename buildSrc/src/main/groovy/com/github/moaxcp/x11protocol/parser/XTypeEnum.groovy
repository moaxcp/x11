package com.github.moaxcp.x11protocol.parser


import com.github.moaxcp.x11protocol.parser.expression.ExpressionFactory
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.parser.JavaEnum.javaEnum
import static com.github.moaxcp.x11protocol.parser.JavaEnumProperty.javaEnumProperty
import static com.github.moaxcp.x11protocol.parser.JavaListProperty.javaListProperty

class XTypeEnum extends XTypeResolved implements XTypeUnit {
    List<XTypeEnumItem> items = []
    
    static XTypeEnum xTypeEnum(XResult result, Node node) {
        XTypeEnum xEnum = new XTypeEnum(name:node.attributes().get('name'), basePackage: result.basePackage, javaPackage: result.javaPackage)
        node.childNodes().each { Node it ->
            if(it.name() == 'doc') {
                return
            }
            if(it.name() != 'item') {
                throw new IllegalArgumentException("could not parse $it")
            }
            XTypeEnumItem item = new XTypeEnumItem()
            item.name = (String) it.attributes().get('name')
            item.value = ExpressionFactory.getExpression((Node) it.childNodes().next())
            xEnum.items.add(item)
        }
        return xEnum
    }

    @Override
    JavaType getJavaType() {
        return javaEnum(this)
    }

    @Override
    JavaProperty getJavaProperty(XUnitField field) {
        return javaEnumProperty(field)
    }

    @Override
    JavaListProperty getJavaListProperty(XUnitListField field) {
        return javaListProperty(field)
    }
}
