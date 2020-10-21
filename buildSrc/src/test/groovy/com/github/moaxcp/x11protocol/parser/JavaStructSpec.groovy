package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec

import static com.github.moaxcp.x11protocol.parser.JavaStruct.javaStruct

class JavaStructSpec extends XmlSpec {
    def 'FormatStruct TypeSpec'() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            struct(name:'Format') {
                field(type: 'CARD8', name: 'depth')
                field(type: 'CARD8', name: 'bitsPerPixel')
                field(type: 'CARD8', name: 'scanlinePad')
                pad(bytes:'5')
            }
        }
        addChildNodes()

        when:
        XTypeStruct struct = result.resolveXType('Format')
        JavaStruct javaStruct = javaStruct(struct)

        then:
        javaStruct.typeSpec.toString() == '''\
            @lombok.Data
            @lombok.AllArgsConstructor
            @lombok.NoArgsConstructor
            public class FormatStruct implements com.github.moaxcp.x11client.protocol.XStruct {
              private byte depth;
            
              private byte bitsPerPixel;
            
              private byte scanlinePad;
            
              public static com.github.moaxcp.x11client.protocol.xproto.FormatStruct readFormatStruct(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                byte depth = in.readCard8();
                byte bitsPerPixel = in.readCard8();
                byte scanlinePad = in.readCard8();
                in.readPad(5);
                com.github.moaxcp.x11client.protocol.xproto.FormatStruct javaObject = new com.github.moaxcp.x11client.protocol.xproto.FormatStruct();
                javaObject.setDepth(depth);
                javaObject.setBitsPerPixel(bitsPerPixel);
                javaObject.setScanlinePad(scanlinePad);
                return javaObject;
              }
              
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(depth);
                out.writeCard8(bitsPerPixel);
                out.writeCard8(scanlinePad);
                out.writePad(5);
              }
              
              @java.lang.Override
              public int getSize() {
                return 1 + 1 + 1 + 5;
              }
            }
        '''.stripIndent()
    }

    def 'ScreenStruct TypeSpec'() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            'enum'(name:'EventMask') {
                item(name:'NoEvent') {
                    value('0')
                }
            }
            'enum'(name:'BackingStore') {
                item(name:'NotUseful') {
                    value('0')
                }
            }
            struct(name:'SCREEN') {
                field(type:'CARD32', name:'root')
                field(type:'CARD32', name:'default_colormap')
                field(type:'CARD32', mask:'EventMask', name:'current_input_masks')
                field(type:'BYTE', name:'backing_stores', enum:'BackingStore')
            }
        }
        addChildNodes()

        when:
        XTypeStruct struct = result.resolveXType('SCREEN')
        JavaStruct javaStruct = javaStruct(struct)

        then:
        javaStruct.typeSpec.toString() == '''\
            @lombok.Data
            @lombok.AllArgsConstructor
            @lombok.NoArgsConstructor
            public class ScreenStruct implements com.github.moaxcp.x11client.protocol.XStruct {
              private int root;
            
              private int defaultColormap;
            
              private int currentInputMasks;
            
              private com.github.moaxcp.x11client.protocol.xproto.BackingStoreEnum backingStores;
            
              public static com.github.moaxcp.x11client.protocol.xproto.ScreenStruct readScreenStruct(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                int root = in.readCard32();
                int defaultColormap = in.readCard32();
                int currentInputMasks = in.readCard32();
                com.github.moaxcp.x11client.protocol.xproto.BackingStoreEnum backingStores = com.github.moaxcp.x11client.protocol.xproto.BackingStoreEnum.getByCode(in.readByte());
                com.github.moaxcp.x11client.protocol.xproto.ScreenStruct javaObject = new com.github.moaxcp.x11client.protocol.xproto.ScreenStruct();
                javaObject.setRoot(root);
                javaObject.setDefaultColormap(defaultColormap);
                javaObject.setCurrentInputMasks(currentInputMasks);
                javaObject.setBackingStores(backingStores);
                return javaObject;
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard32(root);
                out.writeCard32(defaultColormap);
                out.writeCard32(currentInputMasks);
                out.writeByte((byte) backingStores.getValue());
              }
            
              public void currentInputMasksEnable(
                  com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum maskEnum) {
                currentInputMasks = (int) maskEnum.enableFor(currentInputMasks);
              }
            
              public void currentInputMasksDisable(
                  com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum maskEnum) {
                currentInputMasks = (int) maskEnum.disableFor(currentInputMasks);
              }
              
              @java.lang.Override
              public int getSize() {
                return 4 + 4 + 4 + 1;
              }
            }
        '''.stripIndent()
    }

    def 'paramref with DeviceTimeCoord'() {
        given:
        xmlBuilder.xcb(header:'xinput') {
            typedef(oldname:'CARD32', newname:'TIMESTAMP')
            struct(name:'DeviceTimeCoord') {
                field(type:'TIMESTAMP', name:'time')
                list(type:'INT32', name:'axisvalues') {
                    paramref(type:'CARD8', 'num_axes')
                }
            }
        }
        addChildNodes()

        when:
        XTypeStruct struct = result.resolveXType('DeviceTimeCoord')
        JavaStruct javaStruct = javaStruct(struct)

        then:
        javaStruct.typeSpec.toString() == '''\
            @lombok.Data
            @lombok.AllArgsConstructor
            @lombok.NoArgsConstructor
            public class DeviceTimeCoordStruct implements com.github.moaxcp.x11client.protocol.XStruct {
              private int time;
            
              private int[] axisvalues;
            
              public static com.github.moaxcp.x11client.protocol.xproto.DeviceTimeCoordStruct readDeviceTimeCoordStruct(
                  byte numAxes, com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                int time = in.readCard32();
                int[] axisvalues = in.readInt32(numAxes);
                com.github.moaxcp.x11client.protocol.xproto.DeviceTimeCoordStruct javaObject = new com.github.moaxcp.x11client.protocol.xproto.DeviceTimeCoordStruct();
                javaObject.setTime(time);
                javaObject.setAxisvalues(axisvalues);
                return javaObject;
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard32(time);
                out.writeInt32(axisvalues);
              }
              
              @java.lang.Override
              public int getSize() {
                return 4 + 4 * axisvalues.length;
              }
            }
        '''.stripIndent()
    }

    def 'fieldref with boolean type'() {
        given:
        xmlBuilder.xcb(header:'xkb') {
            struct(name:'SetKeyType') {
                field(type:'BOOL', name:'preserve')
                field(type:'CARD8', name:'nMapEntries')
                list(type:'INT32', name:'preserve_entries') {
                    op(op:'*') {
                        fieldref("preserve")
                        fieldref("nMapEntries")
                    }
                }
            }
        }
        addChildNodes()

        when:
        XTypeStruct struct = result.resolveXType('SetKeyType')
        JavaStruct javaStruct = javaStruct(struct)

        then:
        javaStruct.typeSpec.toString() == '''\
            @lombok.Data
            @lombok.AllArgsConstructor
            @lombok.NoArgsConstructor
            public class SetKeyTypeStruct implements com.github.moaxcp.x11client.protocol.XStruct {
              private boolean preserve;
            
              private byte nMapEntries;
            
              private int[] preserveEntries;
            
              public static com.github.moaxcp.x11client.protocol.xproto.SetKeyTypeStruct readSetKeyTypeStruct(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                boolean preserve = in.readBool();
                byte nMapEntries = in.readCard8();
                int[] preserveEntries = in.readInt32((preserve ? 1 : 0) * Byte.toUnsignedInt(nMapEntries));
                com.github.moaxcp.x11client.protocol.xproto.SetKeyTypeStruct javaObject = new com.github.moaxcp.x11client.protocol.xproto.SetKeyTypeStruct();
                javaObject.setPreserve(preserve);
                javaObject.setNMapEntries(nMapEntries);
                javaObject.setPreserveEntries(preserveEntries);
                return javaObject;
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeBool(preserve);
                out.writeCard8(nMapEntries);
                out.writeInt32(preserveEntries);
              }
              
              @java.lang.Override
              public int getSize() {
                return 1 + 1 + 4 * preserveEntries.length;
              }
            }
        '''.stripIndent()

    }
}
