package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec

class JavaEventSpec extends XmlSpec {
    def create() {
        given:
        xmlBuilder.xcb() {
            'enum'(name:'CompleteKind') {
                item(name:'Pixmap') {
                    value('0')
                }
                item(name:'NotifyMSC') {
                    value('1')
                }
            }
            event(name:'CompleteNotify', number:'1', xge:'true') {
                required_start_align(align:'8')
                field(type:'CARD8', name:'kind', 'enum':'CompleteKind')

                field(type:'CARD32', name:'serial')
            }
        }

        when:
        addChildNodes()
        XTypeEvent event = result.resolveXType('CompleteNotify')
        JavaEvent javaEvent = event.javaType

        then:
        javaEvent.typeSpec.toString() == '''\
            @lombok.Data
            @lombok.AllArgsConstructor
            @lombok.NoArgsConstructor
            public class CompleteNotifyEvent implements com.github.moaxcp.x11client.protocol.XEvent {
              public static final byte NUMBER = 1;
            
              private byte eventDetail;
            
              private short sequenceNumber;
            
              private com.github.moaxcp.x11client.protocol.xproto.CompleteKindEnum kind;
            
              private int serial;
              
              public byte getNumber() {
                return NUMBER;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.CompleteNotifyEvent readCompleteNotifyEvent(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                byte eventDetail = in.readCard8();
                short sequenceNumber = in.readCard16();
                //todo align;
                com.github.moaxcp.x11client.protocol.xproto.CompleteKindEnum kind = com.github.moaxcp.x11client.protocol.xproto.CompleteKindEnum.getByCode(in.readCard8());
                int serial = in.readCard32();
                com.github.moaxcp.x11client.protocol.xproto.CompleteNotifyEvent javaObject = new com.github.moaxcp.x11client.protocol.xproto.CompleteNotifyEvent();
                javaObject.setEventDetail(eventDetail);
                javaObject.setSequenceNumber(sequenceNumber);
                javaObject.setKind(kind);
                javaObject.setSerial(serial);
                return javaObject;
              }
            
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(eventDetail);
                out.writeCard16(sequenceNumber);
                //todo align;
                out.writeCard8((byte) kind.getValue());
                out.writeCard32(serial);
              }
              
              public int getSize() {
                return 0;
              }
            }
        '''.stripIndent()
    }
}
