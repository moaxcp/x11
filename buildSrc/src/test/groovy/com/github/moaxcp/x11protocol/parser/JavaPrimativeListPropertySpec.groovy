package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.TypeSpec

class JavaPrimativeListPropertySpec extends XmlSpec {
    def create() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            struct(name:'Window') {
                field(name: 'num_window_modifiers', type: 'CARD32')
                list(name: 'window_modifiers', type: 'CARD64') {
                    fieldref('num_window_modifiers')
                }
            }
        }
        addChildNodes()

        when:
        TypeSpec typeSpec = result.resolveXType('Window').javaType.typeSpec

        then:
        typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class WindowStruct implements com.github.moaxcp.x11client.protocol.XStruct {
              private int numWindowModifiers;
             
              @lombok.NonNull
              private java.util.List<java.lang.Long> windowModifiers;
             
              public static com.github.moaxcp.x11client.protocol.xproto.WindowStruct readWindowStruct(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                int numWindowModifiers = in.readCard32();
                java.util.List<java.lang.Long> windowModifiers = in.readCard64((int) (Integer.toUnsignedLong(numWindowModifiers)));
                com.github.moaxcp.x11client.protocol.xproto.WindowStruct.WindowStructBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.WindowStruct.builder();
                javaBuilder.numWindowModifiers(numWindowModifiers);
                javaBuilder.windowModifiers(windowModifiers);
                return javaBuilder.build();
              }
             
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard32(numWindowModifiers);
                out.writeCard64(windowModifiers);
              }
             
              @java.lang.Override
              public int getSize() {
                return 4 + 8 * windowModifiers.size();
              }
             
              public static class WindowStructBuilder {
                public int getSize() {
                  return 4 + 8 * windowModifiers.size();
                }
              }
            }
        '''.stripIndent()
    }
}
