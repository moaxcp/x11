package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.TypeSpec

class CaseTypeSpec extends XmlSpec {
    def setup() {
        xmlBuilder.xcb() {
            typedef(oldname: 'CARD8', newname: 'KeyCode')
            'enum'(name: 'InputClass') {
                item(name: 'key') {
                    value('0')
                }
            }
            struct(name: 'InputInfo') {
                field(type: 'CARD8', name: 'class_id', enum: 'InputClass')
                field(type: 'CARD8', name: 'len')
                'switch'(name: 'info') {
                    fieldref('class_id')
                    required_start_align(align: '4', offset: '2')
                    'case'(name: 'key') {
                        enumref(ref: 'InputClass', "Key")
                        field(type: 'KeyCode', name: 'min_keycode')
                        field(type: 'KeyCode', name: 'max_keycode')
                        field(type: 'CARD16', name: 'num_keys')
                        pad(bytes: '2')
                    }
                    'case'(name: 'button') {
                        enumref(ref: 'InputClass', 'Button')
                        field(type: 'CARD16', name: 'num_buttons')
                    }
                    'case'(name: 'valuator') {
                        enumref(ref: 'InputClass', 'Valuator')
                        required_start_align(align: '4', offset: '2')
                        field(type: 'CARD8', name: 'axes_len')
                        field(type: 'CARD8', name: 'mode', enum: 'ValuatorMode')
                        list(type: 'AxisInfo', name: 'axes') {
                            fieldref('axes_len')
                        }
                    }
                }
            }
        }
    }

    def inputInfo() {
        given:
        addChildNodes()

        when:
        TypeSpec typeSpec = result.resolveXType('InputInfo').javaType.typeSpec

        then:
        typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class InputInfo implements com.github.moaxcp.x11client.protocol.XStruct, com.github.moaxcp.x11client.protocol.xproto.XprotoObject {
              private byte classId;
            
              private byte len;
            
              public static com.github.moaxcp.x11client.protocol.xproto.InputInfo readInputInfo(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                byte classId = in.readCard8();
                byte len = in.readCard8();
                com.github.moaxcp.x11client.protocol.xproto.InputInfo.InputInfoBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.InputInfo.builder();
                javaBuilder.classId(classId);
                javaBuilder.len(len);
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(classId);
                out.writeCard8(len);
              }
            
              @java.lang.Override
              public int getSize() {
                return 2;
              }
            
              public static class InputInfoBuilder {
                public com.github.moaxcp.x11client.protocol.xproto.InputInfo.InputInfoBuilder classId(
                    com.github.moaxcp.x11client.protocol.xproto.InputClass classId) {
                  this.classId = (byte) classId.getValue();
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.InputInfo.InputInfoBuilder classId(
                    byte classId) {
                  this.classId = classId;
                  return this;
                }
            
                public int getSize() {
                  return 2;
                }
              }
            }
        '''.stripIndent()

    }

    def inputInfoKey() {
        given:
        addChildNodes()

        when:
        TypeSpec typeSpec = result.resolveXType('InputInfo').getSubType('key').typeSpec

        then:
        typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class InputInfoKey implements com.github.moaxcp.x11client.protocol.xproto.InputInfo, com.github.moaxcp.x11client.protocol.xproto.XprotoObject {
              private byte classId;
            
              private byte len;
            
              private byte minKeycode;
            
              private byte maxKeycode;
            
              private short numKeys;
            
              public static com.github.moaxcp.x11client.protocol.xproto.InputInfoKey readInputInfoKey(
                  byte classId, byte len, com.github.moaxcp.x11client.protocol.X11Input in) throws
                  java.io.IOException {
                byte minKeycode = in.readCard8();
                byte maxKeycode = in.readCard8();
                short numKeys = in.readCard16();
                in.readPad(2);
                com.github.moaxcp.x11client.protocol.xproto.InputInfoKey.InputInfoKeyBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.InputInfoKey.builder();
                javaBuilder.classId(classId);
                javaBuilder.len(len);
                javaBuilder.minKeycode(minKeycode);
                javaBuilder.maxKeycode(maxKeycode);
                javaBuilder.numKeys(numKeys);
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(classId);
                out.writeCard8(len);
                out.writeCard8(minKeycode);
                out.writeCard8(maxKeycode);
                out.writeCard16(numKeys);
                out.writePad(2);
              }
            
              @java.lang.Override
              public int getSize() {
                return 8;
              }
            
              public static class InputInfoKeyBuilder {
                public com.github.moaxcp.x11client.protocol.xproto.InputInfoKey.InputInfoKeyBuilder classId(
                    com.github.moaxcp.x11client.protocol.xproto.InputClass classId) {
                  this.classId = (byte) classId.getValue();
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.InputInfoKey.InputInfoKeyBuilder classId(
                    byte classId) {
                  this.classId = classId;
                  return this;
                }
            
                public int getSize() {
                  return 8;
                }
              }
            }
        '''.stripIndent()
    }
}
