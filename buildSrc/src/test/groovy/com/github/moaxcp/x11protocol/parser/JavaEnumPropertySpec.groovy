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
              @lombok.NonNull
              private com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum mask;
             
              public static com.github.moaxcp.x11client.protocol.xproto.StructStruct readStructStruct(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum mask = com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum.getByCode(in.readCard8());
                com.github.moaxcp.x11client.protocol.xproto.StructStructBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.StructStructBuilder.builder();
                javaBuilder.mask(mask);
                return javaBuilder.build();
              }
             
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8((byte) mask.getValue());
              }
             
              @java.lang.Override
              public int getSize() {
                return 1;
              }
             
              public static class StructStructBuilder {
                @java.lang.Override
                public int getSize() {
                  return 1;
                }
              }
            }
        '''.stripIndent()
    }
}
