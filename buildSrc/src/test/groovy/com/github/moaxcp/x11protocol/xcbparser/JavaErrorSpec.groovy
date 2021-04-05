package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec

class JavaErrorSpec extends XmlSpec {
    def requestError() {
        given:
        xmlBuilder.xcb() {
            error(name: 'Request', number: '1') {
                field(type:'CARD32', name:'bad_value')
                field(type:'CARD16', name:'minor_opcode')
                field(type:'CARD8', name:'major_opcode')
                pad(bytes:'1')
            }
        }

        when:
        addChildNodes()
        XTypeError error = result.resolveXType('Request')
        JavaError javaError = error.javaType

        then:
        javaError.typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class RequestError implements com.github.moaxcp.x11client.protocol.XError {
              public static final byte CODE = 1;
            
              private short sequenceNumber;
            
              private int badValue;
            
              private short minorOpcode;
            
              private byte majorOpcode;
            
              @java.lang.Override
              public byte getCode() {
                return CODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.RequestError readRequestError(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                short sequenceNumber = in.readCard16();
                int badValue = in.readCard32();
                short minorOpcode = in.readCard16();
                byte majorOpcode = in.readCard8();
                in.readPad(1);
                in.readPad(20);
                com.github.moaxcp.x11client.protocol.xproto.RequestError.RequestErrorBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.RequestError.builder();
                javaBuilder.sequenceNumber(sequenceNumber);
                javaBuilder.badValue(badValue);
                javaBuilder.minorOpcode(minorOpcode);
                javaBuilder.majorOpcode(majorOpcode);
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(getResponseCode());
                out.writeCard8(CODE);
                out.writeCard16(sequenceNumber);
                out.writeCard32(badValue);
                out.writeCard16(minorOpcode);
                out.writeCard8(majorOpcode);
                out.writePad(1);
                out.writePad(20);
              }
            
              @java.lang.Override
              public int getSize() {
                return 32;
              }
            
              public static class RequestErrorBuilder {
                public int getSize() {
                  return 32;
                }
              }
            }
        '''.stripIndent();

    }
}
