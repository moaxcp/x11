package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.TypeSpec

class JavaEnumPropertySpec extends XmlSpec {
    def create() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            "enum"(name: 'EventMask') {
                item(name: 'NoEvent') {
                    value("0")
                }
                item(name: 'KeyPress') {
                    bit('0')
                }
                item(name: 'KeyRelease') {
                    bit('1')
                }
            }
            struct(name:'Struct') {
                field(type:'CARD8', name:'mask', 'enum':'EventMask')
            }
        }
        addChildNodes()

        when:
        TypeSpec typeSpec = result.resolveXType('Struct').javaType.typeSpec

        then:
        typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class StructStruct implements com.github.moaxcp.x11client.protocol.XStruct {
              private byte mask;
            
              public static com.github.moaxcp.x11client.protocol.xproto.StructStruct readStructStruct(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                byte mask = in.readCard8();
                com.github.moaxcp.x11client.protocol.xproto.StructStruct.StructStructBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.StructStruct.builder();
                javaBuilder.mask(mask);
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(mask);
              }
            
              @java.lang.Override
              public int getSize() {
                return 1;
              }
            
              public static class StructStructBuilder {
                public com.github.moaxcp.x11client.protocol.xproto.StructStruct.StructStructBuilder mask(
                    com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum mask) {
                  this.mask = (byte) mask.getValue();
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.StructStruct.StructStructBuilder mask(
                    byte mask) {
                  this.mask = mask;
                  return this;
                }
            
                public int getSize() {
                  return 1;
                }
              }
            }
        '''.stripIndent()
    }
}
