package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec
import com.github.moaxcp.x11protocol.xcbparser.JavaReply
import com.github.moaxcp.x11protocol.xcbparser.XTypeRequest

class SumOfSpec extends XmlSpec {
    def listInputDevices() {
        given:
        xmlBuilder.xcb() {
            typedef(oldname:'CARD32', newname:'ATOM')
            struct(name: 'STR') {
                field(type: 'CARD8', name: 'name_len')
                list(type: 'char', name: 'name') {
                    fieldref('name_len')
                }
            }
            'enum'(name: 'DeviceUse') {
                item(name: 'IsXPointer') {
                    value('0')
                }
            }
            struct(name: 'DeviceInfo') {
                field(type: 'ATOM', name: 'device_type')
                field(type: 'CARD8', name: 'device_id')
                field(type: 'CARD8', name: 'num_class_info')
                field(type: 'CARD8', name: 'device_use', enum: 'DeviceUse')
                pad(bytes: '1')
            }
            struct(name: 'InputInfo') {
                field(type: 'CARD8', name: 'class_id', enum: 'InputClass')
                field(type: 'CARD8', name: 'len')
            }
            request(name: 'ListInputDevices', opcode: '2') {
                reply {
                    field(type: 'CARD8', name: 'xi_reply_type')
                    field(type: 'CARD8', name: 'devices_len')
                    pad(bytes: '23')
                    list(type: 'DeviceInfo', name: 'devices') {
                        fieldref('devices_len')
                    }
                    list(type: 'InputInfo', name: 'infos') {
                        sumof(ref: 'devices') {
                            fieldref('num_class_info')
                        }
                    }
                    list(type: 'STR', name: 'names') {
                        fieldref('devices_len')
                    }
                    pad(align: '4')
                }
            }
        }

        when:
        addChildNodes()
        XTypeRequest request = result.resolveXType('ListInputDevices')
        JavaReply javaReply = request.reply.javaType

        then:
        javaReply.typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class ListInputDevicesReply implements com.github.moaxcp.x11client.protocol.XReply {
              public static final java.lang.String PLUGIN_NAME = "xproto";
            
              private byte xiReplyType;
            
              private short sequenceNumber;
            
              @lombok.NonNull
              private java.util.List<com.github.moaxcp.x11client.protocol.xproto.DeviceInfo> devices;
            
              @lombok.NonNull
              private java.util.List<com.github.moaxcp.x11client.protocol.xproto.InputInfo> infos;
            
              @lombok.NonNull
              private java.util.List<com.github.moaxcp.x11client.protocol.xproto.Str> names;
            
              public static com.github.moaxcp.x11client.protocol.xproto.ListInputDevicesReply readListInputDevicesReply(
                  byte xiReplyType, short sequenceNumber, com.github.moaxcp.x11client.protocol.X11Input in)
                  throws java.io.IOException {
                com.github.moaxcp.x11client.protocol.xproto.ListInputDevicesReply.ListInputDevicesReplyBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.ListInputDevicesReply.builder();
                int length = in.readCard32();
                byte devicesLen = in.readCard8();
                byte[] pad5 = in.readPad(23);
                java.util.List<com.github.moaxcp.x11client.protocol.xproto.DeviceInfo> devices = new java.util.ArrayList<>(Byte.toUnsignedInt(devicesLen));
                for(int i = 0; i < Byte.toUnsignedInt(devicesLen); i++) {
                  devices.add(com.github.moaxcp.x11client.protocol.xproto.DeviceInfo.readDeviceInfo(in));
                }
                java.util.List<com.github.moaxcp.x11client.protocol.xproto.InputInfo> infos = new java.util.ArrayList<>(devices.stream().mapToInt(o -> Byte.toUnsignedInt(o.getNumClassInfo())).sum());
                for(int i = 0; i < devices.stream().mapToInt(o -> Byte.toUnsignedInt(o.getNumClassInfo())).sum(); i++) {
                  infos.add(com.github.moaxcp.x11client.protocol.xproto.InputInfo.readInputInfo(in));
                }
                java.util.List<com.github.moaxcp.x11client.protocol.xproto.Str> names = new java.util.ArrayList<>(Byte.toUnsignedInt(devicesLen));
                for(int i = 0; i < Byte.toUnsignedInt(devicesLen); i++) {
                  names.add(com.github.moaxcp.x11client.protocol.xproto.Str.readStr(in));
                }
                in.readPadAlign(Byte.toUnsignedInt(devicesLen));
                javaBuilder.xiReplyType(xiReplyType);
                javaBuilder.sequenceNumber(sequenceNumber);
                javaBuilder.devices(devices);
                javaBuilder.infos(infos);
                javaBuilder.names(names);
                if(javaBuilder.getSize() < 32) {
                  in.readPad(32 - javaBuilder.getSize());
                }
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(getResponseCode());
                out.writeCard8(xiReplyType);
                out.writeCard16(sequenceNumber);
                out.writeCard32(getLength());
                byte devicesLen = (byte) names.size();
                out.writeCard8(devicesLen);
                out.writePad(23);
                for(com.github.moaxcp.x11client.protocol.xproto.DeviceInfo t : devices) {
                  t.write(out);
                }
                for(com.github.moaxcp.x11client.protocol.xproto.InputInfo t : infos) {
                  t.write(out);
                }
                for(com.github.moaxcp.x11client.protocol.xproto.Str t : names) {
                  t.write(out);
                }
                out.writePadAlign(Byte.toUnsignedInt(devicesLen));
                out.writePadAlign(getSize());
              }
            
              @java.lang.Override
              public int getSize() {
                return 32 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(devices) + com.github.moaxcp.x11client.protocol.XObject.sizeOf(infos) + com.github.moaxcp.x11client.protocol.XObject.sizeOf(names) + com.github.moaxcp.x11client.protocol.XObject.getSizeForPadAlign(4, com.github.moaxcp.x11client.protocol.XObject.sizeOf(names));
              }

              public java.lang.String getPluginName() {
                return PLUGIN_NAME;
              }
            
              public static class ListInputDevicesReplyBuilder {
                public int getSize() {
                  return 32 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(devices) + com.github.moaxcp.x11client.protocol.XObject.sizeOf(infos) + com.github.moaxcp.x11client.protocol.XObject.sizeOf(names) + com.github.moaxcp.x11client.protocol.XObject.getSizeForPadAlign(4, com.github.moaxcp.x11client.protocol.XObject.sizeOf(names));
                }
              }
            }
            '''.stripIndent()
    }
}
