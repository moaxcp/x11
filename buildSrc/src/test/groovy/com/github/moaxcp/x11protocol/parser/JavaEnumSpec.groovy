package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeSpec
import spock.lang.Specification

class JavaEnumSpec extends Specification {
    def 'TypeSpec'() {
        given:
        JavaEnum javaEnum = new JavaEnum(
            simpleName: 'EventMask',
            className: ClassName.get('com.github.moaxcp.x11client.protocol.xproto', 'EventMask'),
            superInterface: ClassName.get('com.github.moaxcp.x11client.protocol', 'IntValue'),
            values: [
                'NoEvent':'0',
                'KeyPress':'0b1',
                'KeyRelease':'0b10'
            ]
        )

        when:
        TypeSpec typeSpec = javaEnum.typeSpec

        then:
        typeSpec.toString() == '''\
            public enum EventMask implements com.github.moaxcp.x11client.protocol.IntValue {
              NoEvent(0),
            
              KeyPress(0b1),
            
              KeyRelease(0b10);
            
              static final java.util.Map<java.lang.Integer, com.github.moaxcp.x11client.protocol.xproto.EventMask> byCode = new java.util.HashMap<>();
            
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
