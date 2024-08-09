package com.github.moaxcp.x11protocol.xcbparser

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
            public class Window implements com.github.moaxcp.x11client.protocol.XStruct {
              public static final java.lang.String PLUGIN_NAME = "xproto";
            
              @lombok.NonNull
              private org.eclipse.collections.api.list.primitive.ImmutableLongList windowModifiers;
            
              public static com.github.moaxcp.x11client.protocol.xproto.Window readWindow(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                com.github.moaxcp.x11client.protocol.xproto.Window.WindowBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.Window.builder();
                int numWindowModifiers = in.readCard32();
                org.eclipse.collections.api.list.primitive.ImmutableLongList windowModifiers = in.readCard64((int) (Integer.toUnsignedLong(numWindowModifiers)));
                javaBuilder.windowModifiers(windowModifiers);
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                int numWindowModifiers = windowModifiers.size();
                out.writeCard32(numWindowModifiers);
                out.writeCard64(windowModifiers);
              }
            
              @java.lang.Override
              public int getSize() {
                return 4 + 8 * windowModifiers.size();
              }
            
              public java.lang.String getPluginName() {
                return PLUGIN_NAME;
              }
            
              public static class WindowBuilder {
                public int getSize() {
                  return 4 + 8 * windowModifiers.size();
                }
              }
            }
            '''.stripIndent()
    }
}
