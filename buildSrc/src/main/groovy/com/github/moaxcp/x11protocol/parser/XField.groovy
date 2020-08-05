package com.github.moaxcp.x11protocol.parser


import groovy.transform.ToString
import groovy.util.slurpersupport.Node

@ToString(includeSuperProperties = true, includePackage = false, includes = ['name', 'type'])
class XField extends ResolvableProperty {
    static XField getXField(XResult result, Node node) {
        String fieldName = node.attributes().get('name')
        String fieldType = node.attributes().get('type')
        String fieldEnum = node.attributes().get('enum')
        String fieldMask = node.attributes().get('mask')
        return new XField(result:result, type:fieldType, enumType:fieldEnum, maskType: fieldMask, name:fieldName)
    }
}
