package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec

class JavaReplySpec extends XmlSpec {
    def queryTree() {
        given:
        xmlBuilder.xcb() {
            xidtype(name:'WINDOW')
            request(name:'QueryTree', opcode:'15') {
                pad(bytes:'1')
                field(type:'WINDOW', name:'window')
                reply() {
                    pad(bytes:'1')
                    field(type:'WINDOW', name:'root')
                    field(type:'WINDOW', name:'parent', altenum:'Window')
                    field(type:'CARD16', name:'children_len')
                    pad(bytes:'14')
                    list(type:'WINDOW', name:'children') {
                        fieldref('children_len')
                    }
                }
            }
        }

        when:
        addChildNodes()
        XTypeRequest request = result.resolveXType('QueryTree')
        JavaRequest javaRequest = request.javaType
        JavaReply javaReply = request.reply.javaType

        then:
        javaRequest.typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class QueryTreeRequest implements com.github.moaxcp.x11client.protocol.TwoWayRequest<com.github.moaxcp.x11client.protocol.xproto.QueryTreeReply> {
              public static final byte OPCODE = 15;
            
              private int window;
            
              public com.github.moaxcp.x11client.protocol.XReplyFunction<com.github.moaxcp.x11client.protocol.xproto.QueryTreeReply> getReplyFunction(
                  ) {
                return (field, sequenceNumber, in) -> com.github.moaxcp.x11client.protocol.xproto.QueryTreeReply.readQueryTreeReply(field, sequenceNumber, in);
              }
            
              public byte getOpCode() {
                return OPCODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.QueryTreeRequest readQueryTreeRequest(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                in.readPad(1);
                short length = in.readCard16();
                int window = in.readCard32();
                com.github.moaxcp.x11client.protocol.xproto.QueryTreeRequest.QueryTreeRequestBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.QueryTreeRequest.builder();
                javaBuilder.window(window);
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(byte offset, com.github.moaxcp.x11client.protocol.X11Output out) throws
                  java.io.IOException {
                out.writeCard8((byte)(java.lang.Byte.toUnsignedInt(OPCODE) + java.lang.Byte.toUnsignedInt(offset)));
                out.writePad(1);
                out.writeCard16((short) getLength());
                out.writeCard32(window);
              }
            
              @java.lang.Override
              public int getSize() {
                return 8;
              }
              
              public static class QueryTreeRequestBuilder {
                public int getSize() {
                  return 8;
                }
              }
            }
        '''.stripIndent()

        javaReply.typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class QueryTreeReply implements com.github.moaxcp.x11client.protocol.XReply {
              private short sequenceNumber;
            
              private int root;
            
              private int parent;
            
              private short childrenLen;
            
              @lombok.NonNull
              private java.util.List<java.lang.Integer> children;
            
              public static com.github.moaxcp.x11client.protocol.xproto.QueryTreeReply readQueryTreeReply(
                  byte pad, short sequenceNumber, com.github.moaxcp.x11client.protocol.X11Input in) throws
                  java.io.IOException {
                int length = in.readCard32();
                int root = in.readCard32();
                int parent = in.readCard32();
                short childrenLen = in.readCard16();
                in.readPad(14);
                java.util.List<java.lang.Integer> children = in.readCard32(Short.toUnsignedInt(childrenLen));
                com.github.moaxcp.x11client.protocol.xproto.QueryTreeReply.QueryTreeReplyBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.QueryTreeReply.builder();
                javaBuilder.sequenceNumber(sequenceNumber);
                javaBuilder.root(root);
                javaBuilder.parent(parent);
                javaBuilder.childrenLen(childrenLen);
                javaBuilder.children(children);
                if(javaBuilder.getSize() < 32) {
                  in.readPad(32 - javaBuilder.getSize());
                }
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(getResponseCode());
                out.writePad(1);
                out.writeCard16(sequenceNumber);
                out.writeCard32(getLength());
                out.writeCard32(root);
                out.writeCard32(parent);
                out.writeCard16(childrenLen);
                out.writePad(14);
                out.writeCard32(children);
                out.writePadAlign(getSize());
              }
            
              @java.lang.Override
              public int getSize() {
                return 32 + 4 * children.size();
              }
              
              public static class QueryTreeReplyBuilder {
                public int getSize() {
                  return 32 + 4 * children.size();
                }
              }
            }
        '''.stripIndent()
    }

    def translateCoordinates() {
        given:
        xmlBuilder.xcb() {
            xidtype(name:'WINDOW')
            request(name:'TranslateCoordinates', opcode:'40') {
                pad(bytes:'1')
                field(type:'WINDOW', name:'src_window')
                field(type:'WINDOW', name:'dst_window')
                field(type:'INT16', name:'src_x')
                field(type:'INT16', name:'src_y')
                reply {
                    field(type:'BOOL', name:'same_screen')
                    field(type:'WINDOW', name:'child', altenum:'Window')
                    field(type:'INT16', name:'dst_y')
                    field(type:'INT16', name:'dst_x')
                }
            }
        }

        when:
        addChildNodes()
        XTypeRequest request = result.resolveXType('TranslateCoordinates')
        JavaRequest javaRequest = request.javaType
        JavaReply javaReply = request.reply.javaType

        then:
        javaReply.typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class TranslateCoordinatesReply implements com.github.moaxcp.x11client.protocol.XReply {
              private boolean sameScreen;
            
              private short sequenceNumber;
            
              private int child;
            
              private short dstY;
            
              private short dstX;
            
              public static com.github.moaxcp.x11client.protocol.xproto.TranslateCoordinatesReply readTranslateCoordinatesReply(
                  byte sameScreen, short sequenceNumber, com.github.moaxcp.x11client.protocol.X11Input in)
                  throws java.io.IOException {
                int length = in.readCard32();
                int child = in.readCard32();
                short dstY = in.readInt16();
                short dstX = in.readInt16();
                in.readPad(16);
                com.github.moaxcp.x11client.protocol.xproto.TranslateCoordinatesReply.TranslateCoordinatesReplyBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.TranslateCoordinatesReply.builder();
                javaBuilder.sameScreen(sameScreen > 0);
                javaBuilder.sequenceNumber(sequenceNumber);
                javaBuilder.child(child);
                javaBuilder.dstY(dstY);
                javaBuilder.dstX(dstX);
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(getResponseCode());
                out.writeBool(sameScreen);
                out.writeCard16(sequenceNumber);
                out.writeCard32(getLength());
                out.writeCard32(child);
                out.writeInt16(dstY);
                out.writeInt16(dstX);
              }
            
              @java.lang.Override
              public int getSize() {
                return 16;
              }
            
              public static class TranslateCoordinatesReplyBuilder {
                public int getSize() {
                  return 16;
                }
              }
            }
        '''.stripIndent()
    }

    def listHosts() {
        given:
        xmlBuilder.xcb() {
            'enum'(name:'AccessControl') {
                item(name:'Disable') {
                    value('0')
                }
                item(name:'Enable') {
                    value('1')
                }
            }
            struct(name:'HOST') {
                field(type:'CARD8', name:'family')
            }
            request(name:'ListHosts', opcode:'110') {
                reply {
                    field(type:'BYTE', name:'mode', enum:'AccessControl')
                    field(type:'CARD16', name:'hosts_len')
                    pad(bytes:'22')
                    list(type:'HOST', name:'hosts') {
                        fieldref('hosts_len')
                    }
                }
            }
        }

        when:
        addChildNodes()
        XTypeRequest request = result.resolveXType('ListHosts')
        JavaRequest javaRequest = request.javaType
        JavaReply javaReply = request.reply.javaType

        then:
        javaReply.typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class ListHostsReply implements com.github.moaxcp.x11client.protocol.XReply {
              @lombok.NonNull
              private com.github.moaxcp.x11client.protocol.xproto.AccessControlEnum mode;
            
              private short sequenceNumber;
            
              private short hostsLen;
            
              @lombok.NonNull
              private java.util.List<com.github.moaxcp.x11client.protocol.xproto.HostStruct> hosts;
            
              public static com.github.moaxcp.x11client.protocol.xproto.ListHostsReply readListHostsReply(
                  byte mode, short sequenceNumber, com.github.moaxcp.x11client.protocol.X11Input in) throws
                  java.io.IOException {
                int length = in.readCard32();
                short hostsLen = in.readCard16();
                in.readPad(22);
                java.util.List<com.github.moaxcp.x11client.protocol.xproto.HostStruct> hosts = new java.util.ArrayList<>(Short.toUnsignedInt(hostsLen));
                for(int i = 0; i < Short.toUnsignedInt(hostsLen); i++) {
                  hosts.add(com.github.moaxcp.x11client.protocol.xproto.HostStruct.readHostStruct(in));
                }
                com.github.moaxcp.x11client.protocol.xproto.ListHostsReply.ListHostsReplyBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.ListHostsReply.builder();
                javaBuilder.mode(com.github.moaxcp.x11client.protocol.xproto.AccessControlEnum.getByCode(mode));
                javaBuilder.sequenceNumber(sequenceNumber);
                javaBuilder.hostsLen(hostsLen);
                javaBuilder.hosts(hosts);
                if(javaBuilder.getSize() < 32) {
                  in.readPad(32 - javaBuilder.getSize());
                }
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(getResponseCode());
                out.writeByte((byte) mode.getValue());
                out.writeCard16(sequenceNumber);
                out.writeCard32(getLength());
                out.writeCard16(hostsLen);
                out.writePad(22);
                for(com.github.moaxcp.x11client.protocol.xproto.HostStruct t : hosts) {
                  t.write(out);
                }
                out.writePadAlign(getSize());
              }
            
              @java.lang.Override
              public int getSize() {
                return 32 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(hosts);
              }
            
              public static class ListHostsReplyBuilder {
                public int getSize() {
                  return 32 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(hosts);
                }
              }
            }
        '''.stripIndent()
    }

    def queryExtension() {
        given:
        xmlBuilder.xcb() {
            request(name:'QueryExtension', opcode:'98') {
                pad(bytes: '1')
                field(type: 'CARD16', name: 'name_len')
                pad(bytes: '2')
                list(type: 'char', name: 'name') {
                    fieldref('name_len')
                }
                reply {
                    pad(bytes:'1')
                    field(type:'BOOL', name:'present')
                    field(type:'CARD8', name:'major_opcode')
                    field(type:'CARD8', name:'first_event')
                    field(type:'CARD8', name:'first_error')
                }
            }
        }

        when:
        addChildNodes()
        XTypeRequest request = result.resolveXType('QueryExtension')
        JavaRequest javaRequest = request.javaType
        JavaReply javaReply = request.reply.javaType

        then:
        javaReply.typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class QueryExtensionReply implements com.github.moaxcp.x11client.protocol.XReply {
              private short sequenceNumber;
            
              private boolean present;
            
              private byte majorOpcode;
            
              private byte firstEvent;
            
              private byte firstError;
            
              public static com.github.moaxcp.x11client.protocol.xproto.QueryExtensionReply readQueryExtensionReply(
                  byte pad, short sequenceNumber, com.github.moaxcp.x11client.protocol.X11Input in) throws
                  java.io.IOException {
                int length = in.readCard32();
                boolean present = in.readBool();
                byte majorOpcode = in.readCard8();
                byte firstEvent = in.readCard8();
                byte firstError = in.readCard8();
                in.readPad(20);
                com.github.moaxcp.x11client.protocol.xproto.QueryExtensionReply.QueryExtensionReplyBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.QueryExtensionReply.builder();
                javaBuilder.sequenceNumber(sequenceNumber);
                javaBuilder.present(present);
                javaBuilder.majorOpcode(majorOpcode);
                javaBuilder.firstEvent(firstEvent);
                javaBuilder.firstError(firstError);
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(getResponseCode());
                out.writePad(1);
                out.writeCard16(sequenceNumber);
                out.writeCard32(getLength());
                out.writeBool(present);
                out.writeCard8(majorOpcode);
                out.writeCard8(firstEvent);
                out.writeCard8(firstError);
              }
            
              @java.lang.Override
              public int getSize() {
                return 12;
              }
          
              public static class QueryExtensionReplyBuilder {
                public int getSize() {
                  return 12;
                }
              }
            }
        '''.stripIndent()
    }
}
