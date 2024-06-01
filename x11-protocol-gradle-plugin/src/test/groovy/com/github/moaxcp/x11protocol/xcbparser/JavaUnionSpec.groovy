package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec
import com.github.moaxcp.x11protocol.xcbparser.JavaStruct
import com.github.moaxcp.x11protocol.xcbparser.JavaUnion

class JavaUnionSpec extends XmlSpec {
    def 'create behavior union'() {
        given:
        xmlBuilder.xcb() {
            typedef(oldname:'CARD8', newname:'KEYCODE')
            struct(name:'CommonBehavior') {
                field(name:'type', type:'CARD8')
                field(name:'data', type:'CARD8')
            }
            struct(name:'DefaultBehavior') {
                field(name:'type', type:'CARD8')
                pad(bytes:'1')
            }
            typedef(oldname:'DefaultBehavior', newname:'LockBehavior')
            struct(name:'RadioGroupBehavior') {
                field(name:'type', type:'CARD8')
                field(name:'group', type:'CARD8')
            }
            struct(name:'OverlayBehavior') {
                field(name:'type', type:'CARD8')
                field(name:'key', type:'KEYCODE')
            }
            typedef(oldname:'LockBehavior', newname:'PermamentLockBehavior')
            typedef(oldname:'RadioGroupBehavior', newname:'PermamentRadioGroupBehavior')
            typedef(oldname:'OverlayBehavior', newname:'PermamentOverlayBehavior')
            union(name:'Behavior') {
                field(name:'common', type:'CommonBehavior')
                field(name:'default', type:'DefaultBehavior')
                field(name:'lock', type:'LockBehavior')
                field(name:'radioGroup', type:'RadioGroupBehavior')
                field(name:'overlay1', type:'OverlayBehavior')
                field(name:'overlay2', type:'OverlayBehavior')
                field(name:'permamentLock', type:'PermamentLockBehavior')
                field(name:'permamentRadioGroup', type:'PermamentRadioGroupBehavior')
                field(name:'permamentOverlay1', type:'PermamentOverlayBehavior')
                field(name:'permamentOverlay2', type:'PermamentOverlayBehavior')
                field(name:'type', type:'CARD8')
            }
            'enum'(name:'BehaviorType') {
                item(name:'Default') {
                    value('0')
                }
                item(name:'Lock') {
                    value('1')
                }
                item(name:'RadioGroup') {
                    value('2')
                }
                item(name:'Overlay1') {
                    value('3')
                }
                item(name:'Overlay2') {
                    value('4')
                }
                item(name:'PermamentLock') {
                    value('129')
                }
                item(name:'PermamentRadioGroup') {
                    value('130')
                }
                item(name:'PermamentOverlay1') {
                    value('131')
                }
                item(name:'PermamentOverlay2') {
                    value('132')
                }
            }
        }
        addChildNodes()

        when:
        JavaUnion union = result.resolveXType('Behavior').javaType
        JavaStruct common = result.resolveXType('CommonBehavior').javaType
        JavaStruct defaultStruct = result.resolveXType('DefaultBehavior').javaType

        then:
        union.typeSpec.toString() == '''\
            public interface BehaviorUnion {
              static com.github.moaxcp.x11client.protocol.xproto.BehaviorUnion readBehaviorUnion(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                byte type = in.readCard8();
                byte data = in.readCard8();
                com.github.moaxcp.x11client.protocol.xproto.BehaviorType typeEnum = com.github.moaxcp.x11client.protocol.xproto.BehaviorType.getByCode(type);
                if(typeEnum == com.github.moaxcp.x11client.protocol.xproto.BehaviorType.DEFAULT || typeEnum == com.github.moaxcp.x11client.protocol.xproto.BehaviorType.LOCK || typeEnum == com.github.moaxcp.x11client.protocol.xproto.BehaviorType.PERMAMENT_LOCK) {
                  return new com.github.moaxcp.x11client.protocol.xproto.DefaultBehavior(type);
                }
                if(typeEnum == com.github.moaxcp.x11client.protocol.xproto.BehaviorType.RADIO_GROUP || typeEnum == com.github.moaxcp.x11client.protocol.xproto.BehaviorType.PERMAMENT_RADIO_GROUP) {
                  return new com.github.moaxcp.x11client.protocol.xproto.RadioGroupBehavior(type, data);
                }
                if(typeEnum == com.github.moaxcp.x11client.protocol.xproto.BehaviorType.OVERLAY1 || typeEnum == com.github.moaxcp.x11client.protocol.xproto.BehaviorType.OVERLAY2 || typeEnum == com.github.moaxcp.x11client.protocol.xproto.BehaviorType.PERMAMENT_OVERLAY1 || typeEnum == com.github.moaxcp.x11client.protocol.xproto.BehaviorType.PERMAMENT_OVERLAY2) {
                  return new com.github.moaxcp.x11client.protocol.xproto.OverlayBehavior(type, data);
                }
                else {
                  return new com.github.moaxcp.x11client.protocol.xproto.CommonBehavior(type, data);
                }
              }
            
              void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException;
            }
            '''.stripIndent()

        common.typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class CommonBehavior implements com.github.moaxcp.x11client.protocol.xproto.BehaviorUnion, com.github.moaxcp.x11client.protocol.XStruct {
              public static final java.lang.String PLUGIN_NAME = "xproto";
            
              private byte type;
            
              private byte data;
            
              public static com.github.moaxcp.x11client.protocol.xproto.CommonBehavior readCommonBehavior(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                com.github.moaxcp.x11client.protocol.xproto.CommonBehavior.CommonBehaviorBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.CommonBehavior.builder();
                byte type = in.readCard8();
                byte data = in.readCard8();
                javaBuilder.type(type);
                javaBuilder.data(data);
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(type);
                out.writeCard8(data);
              }
            
              @java.lang.Override
              public int getSize() {
                return 2;
              }

              public java.lang.String getPluginName() {
                return PLUGIN_NAME;
              }
            
              public static class CommonBehaviorBuilder {
                public int getSize() {
                  return 2;
                }
              }
            }
            '''.stripIndent()

        defaultStruct.typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class DefaultBehavior implements com.github.moaxcp.x11client.protocol.xproto.BehaviorUnion, com.github.moaxcp.x11client.protocol.XStruct {
              public static final java.lang.String PLUGIN_NAME = "xproto";
            
              private byte type;
            
              public static com.github.moaxcp.x11client.protocol.xproto.DefaultBehavior readDefaultBehavior(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                com.github.moaxcp.x11client.protocol.xproto.DefaultBehavior.DefaultBehaviorBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.DefaultBehavior.builder();
                byte type = in.readCard8();
                byte[] pad1 = in.readPad(1);
                javaBuilder.type(type);
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(type);
                out.writePad(1);
              }
            
              @java.lang.Override
              public int getSize() {
                return 2;
              }

              public java.lang.String getPluginName() {
                return PLUGIN_NAME;
              }
            
              public static class DefaultBehaviorBuilder {
                public int getSize() {
                  return 2;
                }
              }
            }
            '''.stripIndent()
    }
}
