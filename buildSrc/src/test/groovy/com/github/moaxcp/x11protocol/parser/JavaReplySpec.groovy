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
        JavaReply javaReply = request.reply.javaType

        then:
        javaReply.typeSpec.toString() == '''\
            @lombok.Data
            @lombok.AllArgsConstructor
            @lombok.NoArgsConstructor
            public class QueryTreeReply implements com.github.moaxcp.x11client.protocol.XReply {
              private short sequenceNumber;
            
              private int root;
            
              private int parent;
            
              private short childrenLen;
            
              private int[] children;
            
              public static com.github.moaxcp.x11client.protocol.xproto.QueryTreeReply readQueryTreeReply(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                in.readPad(1);
                short sequenceNumber = in.readCard16();
                int length = in.readCard32();
                int root = in.readCard32();
                int parent = in.readCard32();
                short childrenLen = in.readCard16();
                in.readPad(14);
                int[] children = in.readCard32(Short.toUnsignedInt(childrenLen));
                com.github.moaxcp.x11client.protocol.xproto.QueryTreeReply javaObject = new com.github.moaxcp.x11client.protocol.xproto.QueryTreeReply();
                javaObject.setSequenceNumber(sequenceNumber);
                javaObject.setRoot(root);
                javaObject.setParent(parent);
                javaObject.setChildrenLen(childrenLen);
                javaObject.setChildren(children);
                in.readPadAlign(javaObject.getSize());
                return javaObject;
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out, short sequenceNumber) throws
                  java.io.IOException {
                out.writeCard8((byte) 1);
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
                return 1 + 1 + 2 + 4 + 4 + 4 + 2 + 14 + 4 * children.length;
              }
            }
        '''.stripIndent()
    }
}
