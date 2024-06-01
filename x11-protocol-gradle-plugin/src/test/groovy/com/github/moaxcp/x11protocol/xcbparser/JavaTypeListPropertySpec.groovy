package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec
import com.github.moaxcp.x11protocol.xcbparser.JavaType
import com.github.moaxcp.x11protocol.xcbparser.JavaTypeListProperty
import com.github.moaxcp.x11protocol.xcbparser.XUnitListField
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeSpec
import spock.lang.Ignore

import static com.github.moaxcp.x11protocol.xcbparser.JavaTypeListProperty.javaTypeListProperty

class JavaTypeListPropertySpec extends XmlSpec {
    def create() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            struct(name:'FORMAT') {
                field(type:'CARD8', name:'depth')
                field(type:'CARD8', name:'bits_per_pixel')
                field(type:'CARD8', name:'scanline_pad')
                pad(bytes:5)
            }
            struct(name:'Setup') {
                field(type:'CARD8', name:'pixmap_formats_len')
                list(type:'FORMAT', name:'pixmap_formats') {
                    fieldref('pixmap_formats_len')
                }
            }
        }
        addChildNodes()

        when:
        TypeSpec typeSpec = result.resolveXType('Setup').javaType.typeSpec

        then:
        typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class Setup implements com.github.moaxcp.x11client.protocol.XStruct {
              public static final java.lang.String PLUGIN_NAME = "xproto";
            
              @lombok.NonNull
              private java.util.List<com.github.moaxcp.x11client.protocol.xproto.Format> pixmapFormats;
            
              public static com.github.moaxcp.x11client.protocol.xproto.Setup readSetup(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                com.github.moaxcp.x11client.protocol.xproto.Setup.SetupBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.Setup.builder();
                byte pixmapFormatsLen = in.readCard8();
                java.util.List<com.github.moaxcp.x11client.protocol.xproto.Format> pixmapFormats = new java.util.ArrayList<>(Byte.toUnsignedInt(pixmapFormatsLen));
                for(int i = 0; i < Byte.toUnsignedInt(pixmapFormatsLen); i++) {
                  pixmapFormats.add(com.github.moaxcp.x11client.protocol.xproto.Format.readFormat(in));
                }
                javaBuilder.pixmapFormats(pixmapFormats);
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                byte pixmapFormatsLen = (byte) pixmapFormats.size();
                out.writeCard8(pixmapFormatsLen);
                for(com.github.moaxcp.x11client.protocol.xproto.Format t : pixmapFormats) {
                  t.write(out);
                }
              }
            
              @java.lang.Override
              public int getSize() {
                return 1 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(pixmapFormats);
              }

              public java.lang.String getPluginName() {
                return PLUGIN_NAME;
              }
            
              public static class SetupBuilder {
                public int getSize() {
                  return 1 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(pixmapFormats);
                }
              }
            }
            '''.stripIndent()
    }

    @Ignore
    def 'no expression'() {
        /*
        a list may not have an expression. In this case the object has
        a specific size and the list takes the rest of the bytes left
         */
        given:
        xmlBuilder.xcb(header:'present') {
            struct(name:'Notify') {
                field(type:'CARD32', name:'serial')
            }
        }
        addChildNodes()
        XUnitListField field = new XUnitListField(
            result:result,
            name: 'notifies',
            type: 'Notify'
        )
        JavaType javaType = Mock(JavaType)

        when:
        JavaTypeListProperty property = javaTypeListProperty(javaType, field)

        then:
        property.name == 'formats'
        property.baseTypeName == ClassName.get(field.result.javaPackage, 'FormatStruct')
        property.typeName == ParameterizedTypeName.get(ClassName.get(List), property.baseTypeName)
        property.lengthExpression.expression.toString() == '20'
        property.declareAndReadCode.toString() == '''\
            java.util.List<com.github.moaxcp.x11client.protocol.xproto.FormatStruct> formats = new java.util.ArrayList<>();
            for(int i = 0; i < 20; i++) {
              formats.add(FormatStruct.readFormatStruct(in));
            }
            '''.stripIndent()
        property.addWriteCode().toString() == '''\
            for(com.github.moaxcp.x11client.protocol.xproto.FormatStruct t : formats) {
              t.write(out);
            }
            '''.stripIndent()
    }
}
