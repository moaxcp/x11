package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec

class JavaEventSpec extends XmlSpec {
    def keyPres() {
        given:
        xmlBuilder.xcb() {
            xidtype(name:'WINDOW')
            typedef(oldname:'CARD8', newname:'KEYCODE')
            typedef(oldname:'CARD32', newname:'TIMESTAMP')
            'enum'(name:'KeyButMask') {
                item(name:'Shift') {
                    bit('0')
                }
            }
            'enum'(name:'Window') {
                item(name:'None') {
                    value('0')
                }
            }
            event(name:'KeyPress', number:'2') {
                field(type:'KEYCODE', name:'detail')
                field(type:'TIMESTAMP', name:'time')
                field(type:'WINDOW', name:'root')
                field(type:'WINDOW', name:'event')
                field(type:'WINDOW', name:'child', altenum:'Window')
                field(type:'INT16', name:'root_x')
                field(type:'INT16', name:'root_y')
                field(type:'INT16', name:'event_x')
                field(type:'INT16', name:'event_y')
                field(type:'CARD16', name:'state', mask:'KeyButMask')
                field(type:'BOOL', name:'same_screen')
                pad(bytes:'1')
            }
        }

        when:
        addChildNodes()
        XTypeEvent event = result.resolveXType('KeyPress')
        JavaEvent javaEvent = event.javaType

        then:
        javaEvent.typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class KeyPressEvent implements com.github.moaxcp.x11client.protocol.XEvent {
              public static final byte NUMBER = 2;
            
              private boolean sentEvent;
            
              private byte eventDetail;
            
              private short sequenceNumber;
            
              private byte detail;
            
              private int time;
            
              private int root;
            
              private int event;
            
              private int child;
            
              private short rootX;
            
              private short rootY;
            
              private short eventX;
            
              private short eventY;
            
              private short state;
            
              private boolean sameScreen;
            
              @java.lang.Override
              public byte getResponseCode() {
                return NUMBER;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.KeyPressEvent readKeyPressEvent(
                  boolean sentEvent, com.github.moaxcp.x11client.protocol.X11Input in) throws
                  java.io.IOException {
                byte eventDetail = in.readCard8();
                short sequenceNumber = in.readCard16();
                byte detail = in.readCard8();
                int time = in.readCard32();
                int root = in.readCard32();
                int event = in.readCard32();
                int child = in.readCard32();
                short rootX = in.readInt16();
                short rootY = in.readInt16();
                short eventX = in.readInt16();
                short eventY = in.readInt16();
                short state = in.readCard16();
                boolean sameScreen = in.readBool();
                in.readPad(1);
                com.github.moaxcp.x11client.protocol.xproto.KeyPressEventBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.KeyPressEventBuilder.builder();
                javaBuilder.eventDetail(eventDetail);
                javaBuilder.sequenceNumber(sequenceNumber);
                javaBuilder.detail(detail);
                javaBuilder.time(time);
                javaBuilder.root(root);
                javaBuilder.event(event);
                javaBuilder.child(child);
                javaBuilder.rootX(rootX);
                javaBuilder.rootY(rootY);
                javaBuilder.eventX(eventX);
                javaBuilder.eventY(eventY);
                javaBuilder.state(state);
                javaBuilder.sameScreen(sameScreen);
                
                javaBuilder.sentEvent(sentEvent);
                if(javaBuilder.getSize() < 32) {
                  in.readPad(32 - javaBuilder.getSize());
                }
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(sentEvent ? 0b10000000 & NUMBER : NUMBER);
                out.writeCard8(eventDetail);
                out.writeCard16(sequenceNumber);
                out.writeCard8(detail);
                out.writeCard32(time);
                out.writeCard32(root);
                out.writeCard32(event);
                out.writeCard32(child);
                out.writeInt16(rootX);
                out.writeInt16(rootY);
                out.writeInt16(eventX);
                out.writeInt16(eventY);
                out.writeCard16(state);
                out.writeBool(sameScreen);
                out.writePad(1);
                if(getSize() < 32) {
                  out.writePad(32 - getSize());
                }
              }
            
              public boolean isStateEnabled(
                  com.github.moaxcp.x11client.protocol.xproto.KeyButMaskEnum maskEnum) {
                return maskEnum.isEnabled(state);
              }
            
              @java.lang.Override
              public int getSize() {
                return 1 + 2 + 1 + 4 + 4 + 4 + 4 + 2 + 2 + 2 + 2 + 2 + 1 + 1;
              }
            
              public static class KeyPressEventBuilder {
                public com.github.moaxcp.x11client.protocol.xproto.KeyPressEvent.KeyPressEventBuilder stateEnable(
                    com.github.moaxcp.x11client.protocol.xproto.KeyButMaskEnum maskEnum) {
                  state = (short) maskEnum.enableFor(state);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.KeyPressEvent.KeyPressEventBuilder stateDisable(
                    com.github.moaxcp.x11client.protocol.xproto.KeyButMaskEnum maskEnum) {
                  state = (short) maskEnum.disableFor(state);
                  return this;
                }
            
                @java.lang.Override
                public int getSize() {
                  return 1 + 2 + 1 + 4 + 4 + 4 + 4 + 2 + 2 + 2 + 2 + 2 + 1 + 1;
                }
              }
            }
        '''.stripIndent()
    }

    def mapRequest() {
        given:
        xmlBuilder.xcb() {
            xidtype(name: 'WINDOW')
            event(name:'MapRequest', number:'20') {
                pad(bytes:'1')
                field(type:'WINDOW', name:'parent')
                field(type:'WINDOW', name:'window')
            }
        }

        when:
        addChildNodes()
        XTypeEvent event = result.resolveXType('MapRequest')
        JavaEvent javaEvent = event.javaType

        then:
        javaEvent.typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class MapRequestEvent implements com.github.moaxcp.x11client.protocol.XEvent {
              public static final byte NUMBER = 20;
            
              private boolean sentEvent;
            
              private byte eventDetail;
            
              private short sequenceNumber;
            
              private int parent;
            
              private int window;
            
              @java.lang.Override
              public byte getResponseCode() {
                return NUMBER;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.MapRequestEvent readMapRequestEvent(
                  boolean sentEvent, com.github.moaxcp.x11client.protocol.X11Input in) throws
                  java.io.IOException {
                byte eventDetail = in.readCard8();
                short sequenceNumber = in.readCard16();
                in.readPad(1);
                int parent = in.readCard32();
                int window = in.readCard32();
                com.github.moaxcp.x11client.protocol.xproto.MapRequestEventBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.MapRequestEventBuilder.builder();
                javaBuilder.eventDetail(eventDetail);
                javaBuilder.sequenceNumber(sequenceNumber);
                javaBuilder.parent(parent);
                javaBuilder.window(window);
                
                javaBuilder.sentEvent(sentEvent);
                if(javaBuilder.getSize() < 32) {
                  in.readPad(32 - javaBuilder.getSize());
                }
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(sentEvent ? 0b10000000 & NUMBER : NUMBER);
                out.writeCard8(eventDetail);
                out.writeCard16(sequenceNumber);
                out.writePad(1);
                out.writeCard32(parent);
                out.writeCard32(window);
                if(getSize() < 32) {
                  out.writePad(32 - getSize());
                }
              }
            
              @java.lang.Override
              public int getSize() {
                return 1 + 2 + 1 + 4 + 4;
              }
              
              public static class MapRequestEventBuilder {
                @java.lang.Override
                public int getSize() {
                  return 1 + 2 + 1 + 4 + 4;
                }
              }
            }
        '''.stripIndent()

    }
}
