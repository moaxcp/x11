package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec

class JavaEventSpec extends XmlSpec {
    def create() {
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
            @lombok.Data
            @lombok.AllArgsConstructor
            @lombok.NoArgsConstructor
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
            
              public byte getNumber() {
                return NUMBER;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.KeyPressEvent readKeyPressEvent(
                  com.github.moaxcp.x11client.protocol.X11Input in, boolean sentEvent) throws
                  java.io.IOException {
                this.sentEvent = sentEvent;
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
                com.github.moaxcp.x11client.protocol.xproto.KeyPressEvent javaObject = new com.github.moaxcp.x11client.protocol.xproto.KeyPressEvent();
                javaObject.setEventDetail(eventDetail);
                javaObject.setSequenceNumber(sequenceNumber);
                javaObject.setDetail(detail);
                javaObject.setTime(time);
                javaObject.setRoot(root);
                javaObject.setEvent(event);
                javaObject.setChild(child);
                javaObject.setRootX(rootX);
                javaObject.setRootY(rootY);
                javaObject.setEventX(eventX);
                javaObject.setEventY(eventY);
                javaObject.setState(state);
                javaObject.setSameScreen(sameScreen);
                return javaObject;
              }
            
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
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
              }
            
              public void stateEnable(com.github.moaxcp.x11client.protocol.xproto.KeyButMaskEnum maskEnum) {
                state = (short) maskEnum.enableFor(state);
              }
            
              public void stateDisable(com.github.moaxcp.x11client.protocol.xproto.KeyButMaskEnum maskEnum) {
                state = (short) maskEnum.disableFor(state);
              }
            
              public int getSize() {
                return 2;
              }
            }
        '''.stripIndent()
    }
}