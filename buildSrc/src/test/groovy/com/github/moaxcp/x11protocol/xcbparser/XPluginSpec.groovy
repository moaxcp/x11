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
              @lombok.Getter
              @lombok.Setter
              private byte majorOpcode;
            
              @lombok.Getter
              @lombok.Setter
              private byte firstEvent;
            
              @lombok.Getter
              @lombok.Setter
              private byte firstError;
            
              public java.lang.String getPluginName() {
                return "xinput";
              }
            
              public java.util.Optional<java.lang.String> getExtensionXName() {
                return java.util.Optional.ofNullable("XInputExtension");
              }
            
              public java.util.Optional<java.lang.String> getExtensionName() {
                return java.util.Optional.ofNullable("Input");
              }
            
              public java.util.Optional<java.lang.Boolean> getExtensionMultiword() {
                return java.util.Optional.ofNullable(true);
              }
            
              public java.util.Optional<java.lang.Byte> getMajorVersion() {
                return java.util.Optional.ofNullable((byte) 2);
              }
            
              public java.util.Optional<java.lang.Byte> getMinorVersion() {
                return java.util.Optional.ofNullable((byte) 3);
              }
            
              @java.lang.Override
              public boolean supportedRequest(com.github.moaxcp.x11client.protocol.XRequest request) {
                return request.getPluginName().equals(getPluginName());
              }
            
              @java.lang.Override
              public boolean supportedRequest(byte majorOpcode, byte minorOpcode) {
                boolean isMajorOpcode = majorOpcode == getMajorOpcode();
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
              public com.github.moaxcp.x11client.protocol.XRequest readRequest(byte majorOpcode,
                  byte minorOpcode, com.github.moaxcp.x11client.protocol.X11Input in) throws
                  java.io.IOException {
                throw new java.lang.IllegalArgumentException("majorOpcode " + majorOpcode + ", minorOpcode " + minorOpcode + " is not supported");
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
