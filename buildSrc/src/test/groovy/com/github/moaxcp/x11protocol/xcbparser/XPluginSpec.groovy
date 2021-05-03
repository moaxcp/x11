package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec

class XPluginSpec extends XmlSpec {
    def xinputPlugin() {
        given:
        xmlBuilder.xcb(header: 'xinput', 'extension-xname': 'XInputExtension', 'extension-name': 'Input',
            'major-version': '2', 'minor-version': '3') {
            event(name: 'DeviceValuator', number: '0') {
                field(type: 'CARD8', name: 'device_id')
            }
            event(name: 'DeviceKeyPress', number: '1') {
                field(type: 'BYTE', name: 'detail')
            }
            error(name: 'Device', number: '0')
            error(name: 'Event', number: '1')
        }

        when:
        parseXml()

        then:
        result.getXPlugin().toString() == '''\
            public class XinputPlugin implements com.github.moaxcp.x11client.protocol.XProtocolPlugin {
              public static final java.lang.String NAME = "XInputExtension";
            
              @lombok.Getter
              private byte majorVersion = 2;
            
              @lombok.Getter
              private byte minorVersion = 3;
            
              @lombok.Getter
              @lombok.Setter
              private byte firstEvent;
            
              @lombok.Getter
              @lombok.Setter
              private byte firstError;
            
              public java.lang.String getName() {
                return NAME;
              }
            
              @java.lang.Override
              public boolean supportedRequest(com.github.moaxcp.x11client.protocol.XRequest request) {
                return false;
              }
            
              @java.lang.Override
              public boolean supportedEvent(byte number) {
                if(number - firstEvent == 0) {
                  return true;
                }
                if(number - firstEvent == 1) {
                  return true;
                }
                return false;
              }
            
              @java.lang.Override
              public boolean supportedError(byte code) {
                if(code - firstError == 0) {
                  return true;
                }
                if(code - firstError == 1) {
                  return true;
                }
                return false;
              }
            
              @java.lang.Override
              public com.github.moaxcp.x11client.protocol.XEvent readEvent(byte number, boolean sentEvent,
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                if(number - firstEvent == 0) {
                  return com.github.moaxcp.x11client.protocol.xinput.DeviceValuatorEvent.readDeviceValuatorEvent(firstEvent, sentEvent, in);
                }
                if(number - firstEvent == 1) {
                  return com.github.moaxcp.x11client.protocol.xinput.DeviceKeyPressEvent.readDeviceKeyPressEvent(firstEvent, sentEvent, in);
                }
                throw new java.lang.IllegalArgumentException("number " + number + " is not supported");
              }
            
              @java.lang.Override
              public com.github.moaxcp.x11client.protocol.XError readError(byte code,
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                if(code - firstError == 0) {
                  return com.github.moaxcp.x11client.protocol.xinput.DeviceError.readDeviceError(firstError, in);
                }
                if(code - firstError == 1) {
                  return com.github.moaxcp.x11client.protocol.xinput.EventError.readEventError(firstError, in);
                }
                throw new java.lang.IllegalArgumentException("code " + code + " is not supported");
              }
            
              @java.lang.Override
              public com.github.moaxcp.x11client.protocol.XGenericEvent readGenericEvent(boolean sentEvent,
                  byte extension, short sequenceNumber, int length, short eventType,
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                throw new java.lang.IllegalArgumentException("eventType " + eventType + " is not supported");
              }
            }
        '''.stripIndent()
    }
}
