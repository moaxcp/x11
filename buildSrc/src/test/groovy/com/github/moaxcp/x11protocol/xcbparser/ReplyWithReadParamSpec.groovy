package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec

class ReplyWithReadParamSpec extends XmlSpec {
    def getDeviceMotionEventsReply() {
        given:
        xmlBuilder.xcb() {
            typedef(oldname: 'CARD32', newname: 'TIMESTAMP')
            'enum'(name: 'ValuatorMode') {
                item(name: 'Relative') {
                    value('0')
                }
                item(name: 'Absolute') {
                    value('1')
                }
            }
            struct(name: 'DeviceTimeCoord') {
                field(type: 'TIMESTAMP', name: 'time')
                list(type: 'INT32', name: 'axisvalues') {
                    paramref(type: 'CARD8', 'num_axes')
                }
            }
            request(name: 'GetDeviceMotionEvents', opcode: '10') {
                field(type: 'TIMESTAMP', name: 'start')
                field(type: 'TIMESTAMP', name: 'stop', altenum: 'Time')
                field(type: 'CARD8', name: 'device_id')
                pad(bytes: '3')
                reply {
                    field(type: 'CARD8', name: 'xi_reply_type')
                    field(type: 'CARD32', name: 'num_events')
                    field(type: 'CARD8', name: 'num_axes')
                    field(type: 'CARD8', name: 'device_mode', enum: 'ValuatorMode')
                    pad(bytes: '18')
                    list(type: 'DeviceTimeCoord', name: 'events') {
                        fieldref('num_events')
                    }
                }
            }
        }

        when:
        addChildNodes()
        XTypeRequest request = result.resolveXType('GetDeviceMotionEvents')
        JavaReply javaReply = request.reply.javaType

        then:
        javaReply.typeSpecs[0].toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class GetDeviceMotionEventsReply implements com.github.moaxcp.x11client.protocol.XReply {
              private byte xiReplyType;
            
              private short sequenceNumber;
            
              private byte numAxes;
            
              private byte deviceMode;
            
              @lombok.NonNull
              private java.util.List<com.github.moaxcp.x11client.protocol.xproto.DeviceTimeCoord> events;
            
              public static com.github.moaxcp.x11client.protocol.xproto.GetDeviceMotionEventsReply readGetDeviceMotionEventsReply(
                  byte xiReplyType, short sequenceNumber, com.github.moaxcp.x11client.protocol.X11Input in)
                  throws java.io.IOException {
                int length = in.readCard32();
                int numEvents = in.readCard32();
                byte numAxes = in.readCard8();
                byte deviceMode = in.readCard8();
                in.readPad(18);
                java.util.List<com.github.moaxcp.x11client.protocol.xproto.DeviceTimeCoord> events = new java.util.ArrayList<>((int) (Integer.toUnsignedLong(numEvents)));
                for(int i = 0; i < Integer.toUnsignedLong(numEvents); i++) {
                  events.add(com.github.moaxcp.x11client.protocol.xproto.DeviceTimeCoord.readDeviceTimeCoord(numAxes, in));
                }
                com.github.moaxcp.x11client.protocol.xproto.GetDeviceMotionEventsReply.GetDeviceMotionEventsReplyBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.GetDeviceMotionEventsReply.builder();
                javaBuilder.xiReplyType(xiReplyType);
                javaBuilder.sequenceNumber(sequenceNumber);
                javaBuilder.numAxes(numAxes);
                javaBuilder.deviceMode(deviceMode);
                javaBuilder.events(events);
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
                int numEvents = events.size();
                out.writeCard32(numEvents);
                out.writeCard8(numAxes);
                out.writeCard8(deviceMode);
                out.writePad(18);
                for(com.github.moaxcp.x11client.protocol.xproto.DeviceTimeCoord t : events) {
                  t.write(out);
                }
                out.writePadAlign(getSize());
              }
            
              @java.lang.Override
              public int getSize() {
                return 32 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(events);
              }
            
              public static class GetDeviceMotionEventsReplyBuilder {
                public com.github.moaxcp.x11client.protocol.xproto.GetDeviceMotionEventsReply.GetDeviceMotionEventsReplyBuilder deviceMode(
                    com.github.moaxcp.x11client.protocol.xproto.ValuatorMode deviceMode) {
                  this.deviceMode = (byte) deviceMode.getValue();
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.GetDeviceMotionEventsReply.GetDeviceMotionEventsReplyBuilder deviceMode(
                    byte deviceMode) {
                  this.deviceMode = deviceMode;
                  return this;
                }
            
                public int getSize() {
                  return 32 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(events);
                }
              }
            }
        '''.stripIndent()
    }
}
