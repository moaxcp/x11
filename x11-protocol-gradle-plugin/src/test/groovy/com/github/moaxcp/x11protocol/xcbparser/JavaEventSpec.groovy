package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec
import com.github.moaxcp.x11protocol.xcbparser.JavaEvent
import com.github.moaxcp.x11protocol.xcbparser.XTypeEvent

class JavaEventSpec extends XmlSpec {
    def keyPress() {
        given:
        xmlBuilder.xcb(header: "xproto") {
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
              public static final java.lang.String PLUGIN_NAME = "xproto";
            
              public static final byte NUMBER = 2;
            
              private byte firstEventOffset;
            
              private boolean sentEvent;
            
              private byte detail;
            
              private short sequenceNumber;
            
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
                return (byte) (firstEventOffset + NUMBER);
              }
            
              @java.lang.Override
              public byte getNumber() {
                return NUMBER;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.KeyPressEvent readKeyPressEvent(
                  byte firstEventOffset, boolean sentEvent, com.github.moaxcp.x11client.protocol.X11Input in)
                  throws java.io.IOException {
                com.github.moaxcp.x11client.protocol.xproto.KeyPressEvent.KeyPressEventBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.KeyPressEvent.builder();
                byte detail = in.readCard8();
                short sequenceNumber = in.readCard16();
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
                byte[] pad13 = in.readPad(1);
                javaBuilder.detail(detail);
                javaBuilder.sequenceNumber(sequenceNumber);
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
                javaBuilder.firstEventOffset(firstEventOffset);
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
                out.writeCard8(detail);
                out.writeCard16(sequenceNumber);
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
              }
            
              public boolean isStateEnabled(
                  @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.KeyButMask... maskEnums) {
                for(com.github.moaxcp.x11client.protocol.xproto.KeyButMask m : maskEnums) {
                  if(!m.isEnabled(state)) {
                    return false;
                  }
                }
                return true;
              }
            
              @java.lang.Override
              public int getSize() {
                return 32;
              }
            
              public java.lang.String getPluginName() {
                return PLUGIN_NAME;
              }
            
              public static class KeyPressEventBuilder {
                public boolean isStateEnabled(
                    @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.KeyButMask... maskEnums) {
                  for(com.github.moaxcp.x11client.protocol.xproto.KeyButMask m : maskEnums) {
                    if(!m.isEnabled(state)) {
                      return false;
                    }
                  }
                  return true;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.KeyPressEvent.KeyPressEventBuilder stateEnable(
                    com.github.moaxcp.x11client.protocol.xproto.KeyButMask... maskEnums) {
                  for(com.github.moaxcp.x11client.protocol.xproto.KeyButMask m : maskEnums) {
                    state((short) m.enableFor(state));
                  }
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.KeyPressEvent.KeyPressEventBuilder stateDisable(
                    com.github.moaxcp.x11client.protocol.xproto.KeyButMask... maskEnums) {
                  for(com.github.moaxcp.x11client.protocol.xproto.KeyButMask m : maskEnums) {
                    state((short) m.disableFor(state));
                  }
                  return this;
                }
            
                public int getSize() {
                  return 32;
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
              public static final java.lang.String PLUGIN_NAME = "xproto";
            
              public static final byte NUMBER = 20;
              
              private byte firstEventOffset;
            
              private boolean sentEvent;
            
              private short sequenceNumber;
            
              private int parent;
            
              private int window;
            
              @java.lang.Override
              public byte getResponseCode() {
                return (byte) (firstEventOffset + NUMBER);
              }
              
              @java.lang.Override
              public byte getNumber() {
                return NUMBER;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.MapRequestEvent readMapRequestEvent(
                  byte firstEventOffset, boolean sentEvent, com.github.moaxcp.x11client.protocol.X11Input in)
                  throws java.io.IOException {
                com.github.moaxcp.x11client.protocol.xproto.MapRequestEvent.MapRequestEventBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.MapRequestEvent.builder();
                byte[] pad1 = in.readPad(1);
                short sequenceNumber = in.readCard16();
                int parent = in.readCard32();
                int window = in.readCard32();
                byte[] pad5 = in.readPad(20);
                javaBuilder.sequenceNumber(sequenceNumber);
                javaBuilder.parent(parent);
                javaBuilder.window(window);
                
                javaBuilder.sentEvent(sentEvent);
                javaBuilder.firstEventOffset(firstEventOffset);
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
                out.writePad(1);
                out.writeCard16(sequenceNumber);
                out.writeCard32(parent);
                out.writeCard32(window);
                out.writePad(20);
              }
            
              @java.lang.Override
              public int getSize() {
                return 32;
              }
            
              public java.lang.String getPluginName() {
                return PLUGIN_NAME;
              }
              
              public static class MapRequestEventBuilder {
                public int getSize() {
                  return 32;
                }
              }
            }
            '''.stripIndent()
    }
}
