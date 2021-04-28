package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression
import com.squareup.javapoet.ClassName
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.generator.Conventions.convertX11VariableNameToJava
import static com.github.moaxcp.x11protocol.xcbparser.JavaTypeListProperty.javaTypeListProperty
import static com.github.moaxcp.x11protocol.xcbparser.JavaTypeProperty.javaTypeProperty
import static com.github.moaxcp.x11protocol.xcbparser.XUnitExprField.xUnitExprField
import static com.github.moaxcp.x11protocol.xcbparser.XUnitField.xUnitField
import static com.github.moaxcp.x11protocol.xcbparser.XUnitField.xUnitFieldFd
import static com.github.moaxcp.x11protocol.xcbparser.XUnitListField.xUnitListField
import static com.github.moaxcp.x11protocol.xcbparser.XUnitPadFactory.xUnitPad

abstract class XTypeObject extends XType implements XTypeUnit {
    Set<ClassName> superTypes = []
    List<XUnit> protocol = []

    XTypeObject(Map map) {
        super(map)
        superTypes = map.superTypes ?: []
        protocol = map.protocol ?: []
    }

    XUnitField getField(String name) {
        return protocol.find {
            it.name == name
        }
    }

    XUnitListField getListField(String name) {
        return protocol.find {
            it.name == name
        }
    }

    void addUnits(XResult result, Node node) {
        node.childNodes().each { Node it ->
            List<XUnit> unit = parseXUnit(result, it)
            protocol.addAll(unit)
        }
    }

    List<XUnit> parseXUnit(XResult result, Node node) {
        switch(node.name()) {
            case 'required_start_align':
                return [] //[xUnitRequiredStartAlign(result, node)]
            case 'field':
                return [xUnitField(result, node)]
            case 'fd':
                return [xUnitFieldFd(result, node)]
            case 'list':
                return [xUnitListField(result, node)]
            case 'pad':
                return [xUnitPad(node)]
            case 'switch':
                if(node.childNodes().find { Node it -> it.name() == 'bitcase'}) {
                    return parseValueList(result, node)
                } else if(node.childNodes().find { Node it -> it.name() == 'case'}) {
                    return parseCases(result, node)
                } else {
                    System.out.println('unknown switch')
                    return []
                }
            case 'exprfield':
                return [xUnitExprField(result, node)]
            case 'doc':
                return []
            case 'reply':
                return []
            default:
                throw new IllegalArgumentException("cannot parse ${node.name()}")
        }
    }

    XUnit parseXUnit(XResult result, Node node, XBitcaseInfo bitcaseInfo) {
        switch(node.name()) {
            case 'field':
                return xUnitField(result, node, bitcaseInfo)
            case 'list':
                return xUnitListField(result, node, bitcaseInfo)
            default:
                throw new IllegalArgumentException("cannot parse ${node.name()}")
        }
    }

    List<XUnit> parseValueList(XResult result, Node node) {
        String fieldRef = node.childNodes().find{Node it -> it.name() == 'fieldref'}.text()
        List<XUnit> fields = []
        node.childNodes().each { Node it ->
            if(it.name() == 'fieldref') {
                return
            }
            if(it.name() == 'bitcase') {
                String enumRef
                String enumItem
                it.childNodes().each { Node bitcaseNode ->
                    if(bitcaseNode.name() == 'enumref') {
                        enumRef = bitcaseNode.attributes().get('ref')
                        enumItem = bitcaseNode.text()
                    } else {
                        XUnit unit = parseXUnit(result, bitcaseNode, new XBitcaseInfo(maskField: fieldRef, enumType: enumRef, enumItem: enumItem))
                        fields.add(unit)
                    }
                }
            }
        }
        return fields
    }

    List<XUnit> parseCases(XResult result, Node node) {
        return []
    }

    List<JavaUnit> toJavaProtocol(JavaType javaType) {
        List<JavaUnit> java = protocol.collect {
            it.getJavaUnit(javaType)
        }

        java.eachWithIndex { JavaUnit entry, int i ->
            if(entry instanceof JavaPadAlign) {
                entry.list = (JavaListProperty) java[i - 1]
            }
        }

        java.each { JavaUnit property ->
            if(property instanceof JavaListProperty) {
                List<FieldRefExpression> fieldRefs = property.lengthExpression.fieldRefs
                String lengthField = null
                if(fieldRefs.size() == 1) {
                    lengthField = fieldRefs.get(0).fieldName
                }
                if(lengthField) {
                    JavaPrimativeProperty lengthProperty = (JavaPrimativeProperty) java.find { it instanceof JavaPrimativeProperty && it.name == lengthField }
                    if(lengthProperty) {
                        property.lengthField = convertX11VariableNameToJava(lengthField)
                        lengthProperty.localOnly = true
                        lengthProperty.lengthOfField = property.name
                    }

                }
            }
        }

        return java
    }

    @Override
    JavaTypeProperty getJavaProperty(JavaType javaType, XUnitField field) {
        return javaTypeProperty(javaType, field)
    }

    @Override
    JavaListProperty getJavaProperty(JavaType javaType, XUnitListField field) {
        return javaTypeListProperty(javaType, field)
    }
}
