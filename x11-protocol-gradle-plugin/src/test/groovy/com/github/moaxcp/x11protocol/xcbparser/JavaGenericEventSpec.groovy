package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec
import com.github.moaxcp.x11protocol.xcbparser.JavaEvent
import com.github.moaxcp.x11protocol.xcbparser.XTypeEvent

class JavaGenericEventSpec extends XmlSpec {
    def configureNotify() {
        given:
        xmlBuilder.xcb() {
            xidtype(name: 'EVENT')
            xidtype(name: 'WINDOW')
            event(name: 'ConfigureNotify', number: '0', xge: 'true') {
                pad(bytes: '2')
                field(type: 'EVENT', name: 'event')
                field(type: 'WINDOW', name: 'window')
                field(type: 'INT16', name: 'x')
                field(type: 'INT16', name: 'y')
                field(type: 'CARD16', name: 'width')
                field(type: 'CARD16', name: 'height')
                field(type: 'INT16', name: 'off_x')
                field(type: 'INT16', name: 'off_y')
                field(type: 'CARD16', name: 'pixmap_width')
                field(type: 'CARD16', name: 'pixmap_height')
                field(type: 'CARD32', name: 'pixmap_flags')
            }
        }

        when:
        addChildNodes()
        XTypeEvent event = result.resolveXType('ConfigureNotify')
        JavaEvent javaEvent = event.javaType

        then:
        javaEvent.typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class ConfigureNotifyEvent implements com.github.moaxcp.x11client.protocol.XGenericEvent {
              public static final java.lang.String PLUGIN_NAME = "xproto";
            
              public static final byte NUMBER = 35;
            
              private byte firstEventOffset;
            
              private boolean sentEvent;
            
              private byte extension;
            
              private short sequenceNumber;
            
              private short eventType;
            
              private int event;
            
              private int window;
            
              private short x;
            
              private short y;
            
              private short width;
            
              private short height;
            
              private short offX;
            
              private short offY;
            
              private short pixmapWidth;
            
              private short pixmapHeight;
            
              private int pixmapFlags;
            
              @java.lang.Override
              public byte getResponseCode() {
                return (byte) (firstEventOffset + NUMBER);
              }
            
              @java.lang.Override
              public byte getNumber() {
                return NUMBER;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.ConfigureNotifyEvent readConfigureNotifyEvent(
                  byte firstEventOffset, boolean sentEvent, byte extension, short sequenceNumber, int length,
                  short eventType, com.github.moaxcp.x11client.protocol.X11Input in) throws
                  java.io.IOException {
                com.github.moaxcp.x11client.protocol.xproto.ConfigureNotifyEvent.ConfigureNotifyEventBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.ConfigureNotifyEvent.builder();
                byte[] pad5 = in.readPad(2);
                int event = in.readCard32();
                int window = in.readCard32();
                short x = in.readInt16();
                short y = in.readInt16();
                short width = in.readCard16();
                short height = in.readCard16();
                short offX = in.readInt16();
                short offY = in.readInt16();
                short pixmapWidth = in.readCard16();
                short pixmapHeight = in.readCard16();
                int pixmapFlags = in.readCard32();
                javaBuilder.extension(extension);
                javaBuilder.sequenceNumber(sequenceNumber);
                javaBuilder.eventType(eventType);
                javaBuilder.event(event);
                javaBuilder.window(window);
                javaBuilder.x(x);
                javaBuilder.y(y);
                javaBuilder.width(width);
                javaBuilder.height(height);
                javaBuilder.offX(offX);
                javaBuilder.offY(offY);
                javaBuilder.pixmapWidth(pixmapWidth);
                javaBuilder.pixmapHeight(pixmapHeight);
                javaBuilder.pixmapFlags(pixmapFlags);
            
                javaBuilder.sentEvent(sentEvent);
                javaBuilder.firstEventOffset(firstEventOffset);
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
                out.writeCard8(extension);
                out.writeCard16(sequenceNumber);
                out.writeCard32(getLength() - 32);
                out.writeCard16(eventType);
                out.writePad(2);
                out.writeCard32(event);
                out.writeCard32(window);
                out.writeInt16(x);
                out.writeInt16(y);
                out.writeCard16(width);
                out.writeCard16(height);
                out.writeInt16(offX);
                out.writeInt16(offY);
                out.writeCard16(pixmapWidth);
                out.writeCard16(pixmapHeight);
                out.writeCard32(pixmapFlags);
              }
            
              @java.lang.Override
              public int getSize() {
                return 40;
              }
            
              public java.lang.String getPluginName() {
                return PLUGIN_NAME;
              }
            
              public static class ConfigureNotifyEventBuilder {
                public int getSize() {
                  return 40;
                }
              }
            }
            '''.stripIndent()
    }
}
