package com.github.moaxcp.x11protocol.parser


import groovy.transform.ToString
import groovy.util.slurpersupport.Node

@ToString(includeSuperProperties = true, includePackage = false, includes = ['name', 'type', 'protocol'])
class XStruct extends XType {
    List<XUnit> protocol = []

    static XStruct getXStruct(XResult result, Node node) {
        XStruct struct = new XStruct()
        struct.result = result
        struct.name = node.attributes().get('name')
        struct.type = node.name()
        node.childNodes().each { Node it ->
            switch(it.name()) {
                case 'field':
                    String fieldName = it.attributes().get('name')
                    String fieldType = it.attributes().get('type')
                    XField field = new XField(result:result, type:fieldType, name:fieldName)
                    struct.protocol.add(field)
                    break
                case 'pad':
                    int padBytes = Integer.valueOf((String) it.attributes().get('bytes'))
                    XPad pad = new XPad(bytes:padBytes)
                    struct.protocol.add(pad)
                    break
                default:
                    throw new IllegalArgumentException("cannot parse $it")
            }
        }

        return struct
    }
}
