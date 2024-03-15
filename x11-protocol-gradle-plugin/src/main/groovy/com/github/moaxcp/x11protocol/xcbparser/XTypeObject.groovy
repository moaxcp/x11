package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression
import com.squareup.javapoet.ClassName
import groovy.transform.Memoized
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.generator.Conventions.convertX11VariableNameToJava
import static com.github.moaxcp.x11protocol.xcbparser.JavaTypeListProperty.javaTypeListProperty
import static com.github.moaxcp.x11protocol.xcbparser.JavaTypeProperty.javaTypeProperty
import static com.github.moaxcp.x11protocol.xcbparser.XUnitExprField.xUnitExprField
import static com.github.moaxcp.x11protocol.xcbparser.XUnitField.xUnitField
import static com.github.moaxcp.x11protocol.xcbparser.XUnitField.xUnitFieldFd
import static com.github.moaxcp.x11protocol.xcbparser.XUnitListField.xUnitListField
import static com.github.moaxcp.x11protocol.xcbparser.XUnitPadFactory.xUnitPad
import static com.github.moaxcp.x11protocol.xcbparser.XUnitSwitch.parseXUnitSwitch

abstract class XTypeObject extends XType implements XTypeUnit {
    Set<ClassName> superTypes = []
    List<XUnit> protocol = []

    XTypeObject(Map map) {
        super(map)
        superTypes = map.superTypes ?: []
        protocol = map.protocol ?: []
    }

    @Override
    boolean hasSubTypes() {
        return !subTypeNames.isEmpty()
    }

    @Override
    @Memoized
    List<String> getSubTypeNames() {
        protocol.inject([]) { names, unit ->
            if(unit instanceof XUnitSwitchCase) {
                unit.fields.each {field ->
                    if(field instanceof XUnitField) {
                        names.add(field.caseInfo.enumItem)
                    }
                }
            }
            return names
        }
    }

    @Override
    List<JavaType> getSubTypes() {
        return getSubTypeNames().collect {
            getSubType(it)
        }
    }

    XUnitField getField(String name) {
        return (XUnitField) protocol.find {
            it instanceof XUnitField && it.name == name
        }
    }

    XUnitListField getListField(String name) {
        return (XUnitListField) protocol.find {
            it instanceof XUnitListField && it.name == name
        }
    }

    void addUnits(XResult result, Node node) {
        protocol.addAll(parseAllXUnits(result, node))
    }

    static List<XUnit> parseAllXUnits(XResult result, Node node) {
        return node.childNodes().collect { Node it ->
            parseXUnit(result, it)
        }.inject([]) { list, unit ->
            if(unit) {
                return list + unit
            }
            return list
        }
    }

    static XUnit parseXUnit(XResult result, Node node) {
        switch(node.name()) {
            case 'required_start_align':
                return null //[xUnitRequiredStartAlign(result, node)]
            case 'field':
                return xUnitField(result, node)
            case 'fd':
                return xUnitFieldFd(result, node)
            case 'list':
                return xUnitListField(result, node)
            case 'pad':
                return xUnitPad(node)
            case 'switch':
                return parseXUnitSwitch(result, node)
            case 'exprfield':
                return xUnitExprField(result, node)
            case 'doc':
                return null
            case 'reply':
                return null
            default:
                throw new IllegalArgumentException("cannot parse ${node.name()}")
        }
    }

    static XUnit parseXUnit(XResult result, Node node, XBitcaseInfo bitcaseInfo) {
        XUnit parsed = parseXUnit(result, node)
        parsed.bitcaseInfo = bitcaseInfo
        return parsed
    }

    List<JavaUnit> toJavaProtocol(JavaClass javaClass) {
        List<JavaUnit> java = protocol.collect {
            it.getJavaUnit(javaClass)
        }.flatten()

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
                    JavaPrimitiveProperty lengthProperty = (JavaPrimitiveProperty) java.find { it instanceof JavaPrimitiveProperty && it.name == lengthField }
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
