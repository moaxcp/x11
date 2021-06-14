package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.xcbparser.expression.Expressions
import com.squareup.javapoet.ClassName
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.xcbparser.JavaEnum.javaEnum
import static com.github.moaxcp.x11protocol.xcbparser.JavaEnumListProperty.javaEnumListProperty

class XTypeEnum extends XType implements XTypeUnit {
    List<XTypeEnumItem> items = []

    XTypeEnum(Map map) {
        super(map)
        items = map.items ?: []
    }

    @Override
    Optional<ClassName> getCaseSuperName() {
        return Optional.empty()
    }

    @Override
    List<String> getCaseNames() {
        return []
    }

    @Override
    List<String> getCaseClassNames() {
        return []
    }

    static XTypeEnum xTypeEnum(XResult result, Node node) {
        XTypeEnum xEnum = new XTypeEnum(result: result, name:node.attributes().get('name'), basePackage: result.basePackage, javaPackage: result.javaPackage)
        node.childNodes().each { Node it ->
            if(it.name() == 'doc') {
                return
            }
            if(it.name() != 'item') {
                throw new IllegalArgumentException("could not parse $it")
            }
            XTypeEnumItem item = new XTypeEnumItem()
            item.name = (String) it.attributes().get('name')
            item.value = Expressions.getExpression(null, (Node) it.childNodes().next())
            xEnum.items.add(item)
        }
        return xEnum
    }

    @Override
    List<JavaType> getJavaType() {
        return javaEnum(this)
    }

    @Override
    JavaProperty getJavaProperty(JavaType javaType, XUnitField field) {
        return new JavaEnumProperty(javaType, field)
    }

    @Override
    JavaListProperty getJavaProperty(JavaType javaType, XUnitListField field) {
        return javaEnumListProperty(javaType, field)
    }
}
