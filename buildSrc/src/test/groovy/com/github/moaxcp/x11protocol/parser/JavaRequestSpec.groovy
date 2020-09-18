package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec

class JavaRequestSpec extends XmlSpec {
    def destroyWindow() {
        given:
        xmlBuilder.xcb() {
            xidtype(name:'WINDOW')
            request(name:'DestroyWindow', opcode:'4') {
                pad(bytes: '1')
                field(type: 'WINDOW', name: 'window')
            }
        }

        when:
        addChildNodes()
        XTypeRequest request = result.resolveXType('DestroyWindow')
        JavaRequest javaRequest = request.javaType

        then:
        javaRequest.typeSpec.toString() == '''\
            @lombok.Data
            @lombok.AllArgsConstructor
            @lombok.NoArgsConstructor
            public class DestroyWindowRequest implements com.github.moaxcp.x11client.protocol.XRequest {
              public static final byte OPCODE = 4;
            
              private int window;
            
              public byte getOpCode() {
                return OPCODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.DestroyWindowRequest readDestroyWindowRequest(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                in.readPad(1);
                int window = in.readCard32();
                com.github.moaxcp.x11client.protocol.xproto.DestroyWindowRequest javaObject = new com.github.moaxcp.x11client.protocol.xproto.DestroyWindowRequest();
                javaObject.setWindow(window);
                return javaObject;
              }
            
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(OPCODE);
                out.writePad(1);
                out.writeCard32(window);
              }
            
              public int getSize() {
                return 1 + 4;
              }
            }
        '''.stripIndent()
    }
}
