package com.github.moaxcp.x11protocol.parser

import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.Node
import groovy.xml.MarkupBuilder
import spock.lang.Specification

class XEnumSpec extends Specification {
    StringWriter writer = new StringWriter()
    MarkupBuilder xmlBuilder = new MarkupBuilder(writer)
    XResult result = new XResult(basePackage: 'com.github.moaxcp.x11client.protocol', header:'xproto')

    GPathResult getGPathResult() {
        new XmlSlurper().parseText(writer.toString())
    }

    Node getFirstChild() {
        (Node) getGPathResult().childNodes().next()
    }

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
        result.enums.VisualClass.type == 'enum'
        result.enums.VisualClass.group == 'xproto'
        result.enums.VisualClass.javaClassName == 'VisualClassEnum'
        result.enums.VisualClass.items[0].name == 'StaticGray'
        result.enums.VisualClass.items[0].value.expression == '0'
        result.enums.VisualClass.items[1].name == 'GrayScale'
        result.enums.VisualClass.items[1].value.expression == '1'
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
        result.resolveXType('EventMask').type == 'enum'
        result.resolveXType('EventMask').group == 'xproto'
        result.resolveXType('EventMask').javaClassName == 'EventMaskEnum'
        result.resolveXType('EventMask').items[0].name == 'NoEvent'
        result.resolveXType('EventMask').items[0].value.expression == '0'
        result.resolveXType('EventMask').items[1].name == 'KeyPress'
        result.resolveXType('EventMask').items[1].value.expression == '0b1'
        result.resolveXType('EventMask').items[2].name == 'KeyRelease'
        result.resolveXType('EventMask').items[2].value.expression == '0b10'
    }

    def 'enum typeSpec'() {
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
        result.resolveXType('EventMask').typeSpec.toString() == '''\
            public enum EventMask implements com.github.moaxcp.x11client.protocol.IntValue {
              NoEvent(0),
            
              KeyPress(0b1),
            
              KeyRelease(0b10);
            
              static final java.util.Map<java.lang.Integer, com.github.moaxcp.x11client.protocol.xproto.EventMask> byCode;
            
              static {
                for(com.github.moaxcp.x11client.protocol.xproto.EventMask e : values()) {
                    byCode.put(e.value, e);
                }
              }
            
              private int value;
            
              EventMask(int value) {
                this.value = value;
              }
            
              @java.lang.Override
              public int getValue() {
                return value;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.EventMask getByCode(int code) {
                return byCode.get(code);
              }
            }
        '''.stripIndent()
    }
}
