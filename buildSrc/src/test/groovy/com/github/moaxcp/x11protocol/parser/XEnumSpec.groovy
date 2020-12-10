package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec

class XEnumSpec extends XmlSpec {

    def 'enum values from node'() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            "enum"(name:'VisualClass') {
                item(name:'StaticGray') {
                    value("0")
                }
                item(name:'GrayScale') {
                    value('1')
                }
            }
        }

        when:
        result.addEnum(getFirstChild())

        then:
        result.enums.VisualClass.name == 'VisualClass'
        result.enums.VisualClass.items[0].name == 'StaticGray'
        result.enums.VisualClass.items[0].value.expression.toString() == '0'
        result.enums.VisualClass.items[1].name == 'GrayScale'
        result.enums.VisualClass.items[1].value.expression.toString() == '1'
        result.enums.VisualClass.javaType.simpleName == 'VisualClass'
    }

    def 'enum bit from node'() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            "enum"(name:'EventMask') {
                item(name:'NoEvent') {
                    value("0")
                }
                item(name:'KeyPress') {
                    bit('0')
                }
                item(name:'KeyRelease') {
                    bit('1')
                }
            }
        }

        when:
        result.addEnum(getFirstChild())

        then:
        result.resolveXType('EventMask').name == 'EventMask'
        result.resolveXType('EventMask').items[0].name == 'NoEvent'
        result.resolveXType('EventMask').items[0].value.expression.toString() == '0'
        result.resolveXType('EventMask').items[1].name == 'KeyPress'
        result.resolveXType('EventMask').items[1].value.expression.toString() == '0b1'
        result.resolveXType('EventMask').items[2].name == 'KeyRelease'
        result.resolveXType('EventMask').items[2].value.expression.toString() == '0b10'
        result.resolveXType('EventMask').javaType.simpleName == 'EventMask'
    }
}
