package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.FieldRefExpression
import com.squareup.javapoet.ClassName
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.generator.Conventions.convertX11VariableNameToJava
import static com.github.moaxcp.x11protocol.parser.JavaTypeListProperty.javaTypeListProperty
import static com.github.moaxcp.x11protocol.parser.JavaTypeProperty.javaTypeProperty
import static com.github.moaxcp.x11protocol.parser.XUnitField.xUnitField
import static com.github.moaxcp.x11protocol.parser.XUnitListField.xUnitListField
import static com.github.moaxcp.x11protocol.parser.XUnitPadFactory.xUnitPad
import static com.github.moaxcp.x11protocol.parser.XUnitRequiredStartAlign.xUnitRequiredStartAlign

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
            XUnit unit = parseXUnit(result, it)
            if(unit) {
                protocol.add(unit)
            }
        }
    }

    XUnit parseXUnit(XResult result, Node node) {
        switch(node.name()) {
            case 'required_start_align':
                return xUnitRequiredStartAlign(result, node)
            case 'field':
                return xUnitField(result, node)
            case 'list':
                return xUnitListField(result, node)
            case 'pad':
                return xUnitPad(node)
            case 'switch':
                System.out.println("switch")
                return null
            case 'doc':
                return null
            default:
                throw new IllegalArgumentException("cannot parse ${node.name()}")
        }
    }

    List<JavaUnit> toJavaProtocol(JavaType javaType) {
        List<JavaUnit> java = protocol.collect {
            it.getJavaUnit(javaType)
        }

        java.eachWithIndex { JavaUnit entry, int i ->
            if(entry instanceof JavaPadAlign) {
                entry.list = java[i - 1]
            }
        }

        java.each { JavaUnit property ->
            if(property instanceof JavaPrimativeStringListProperty) {
                List<FieldRefExpression> fieldRefs = property.lengthExpression.fieldRefs
                String lengthField = null
                if(fieldRefs.size() > 1) {
                    lengthField = fieldRefs.find {
                        it.fieldName.endsWith('Len')
                    }?.fieldName
                } else if(fieldRefs.size() == 1) {
                    lengthField = fieldRefs.get(0).fieldName
                }
                if(lengthField) {
                    property.lengthField = convertX11VariableNameToJava(lengthField)
                    JavaPrimativeProperty lengthProperty = java.find { it instanceof JavaPrimativeProperty && it.name == lengthField }
                    lengthProperty.localOnly = true
                    lengthProperty.lengthOfField = property.name
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
