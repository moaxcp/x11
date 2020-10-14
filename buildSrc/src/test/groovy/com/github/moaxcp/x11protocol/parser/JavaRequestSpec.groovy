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
              
              public java.util.Optional<com.github.moaxcp.x11client.protocol.XReplySupplier> getReplySupplier(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                return Optional.empty();
              }
            
              public byte getOpCode() {
                return OPCODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.DestroyWindowRequest readDestroyWindowRequest(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                in.readPad(1);
                short length = in.readCard16();
                int window = in.readCard32();
                com.github.moaxcp.x11client.protocol.xproto.DestroyWindowRequest javaObject = new com.github.moaxcp.x11client.protocol.xproto.DestroyWindowRequest();
                javaObject.setWindow(window);
                return javaObject;
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(OPCODE);
                out.writePad(1);
                out.writeCard16((short) getLength());
                out.writeCard32(window);
              }
            
              @java.lang.Override
              public int getSize() {
                return 1 + 1 + 2 + 4;
              }
            }
        '''.stripIndent()
    }

    def polyPoint() {
        given:
        xmlBuilder.xcb() {
            xidtype(name:'GCONTEXT')
            xidunion(name:'DRAWABLE') {
                type('WINDOW')
                type('PIXMAP')
            }
            'enum'(name:'CoordMode') {
                item(name:'Origin') {
                    value('0')
                }
            }
            struct(name:'POINT') {
                field(type:'INT16', name:'x')
                field(type:'INT16', name:'y')
            }
            request(name:'PolyPoint', opcode:'64') {
                field(type:'BYTE', name:'coordinate_mode', 'enum':'CoordMode')
                field(type:'DRAWABLE', name:'drawable')
                field(type:'GCONTEXT', name:'gc')
                list(type:'POINT', name:'points')
            }
        }

        when:
        addChildNodes()
        XTypeRequest request = result.resolveXType('PolyPoint')
        JavaRequest javaRequest = request.javaType

        then:
        javaRequest.typeSpec.toString() == '''\
            @lombok.Data
            @lombok.AllArgsConstructor
            @lombok.NoArgsConstructor
            public class PolyPointRequest implements com.github.moaxcp.x11client.protocol.XRequest {
              public static final byte OPCODE = 64;
            
              private com.github.moaxcp.x11client.protocol.xproto.CoordModeEnum coordinateMode;
            
              private int drawable;
            
              private int gc;
            
              private java.util.List<com.github.moaxcp.x11client.protocol.xproto.PointStruct> points;
              
              public java.util.Optional<com.github.moaxcp.x11client.protocol.XReplySupplier> getReplySupplier(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                return Optional.empty();
              }
            
              public byte getOpCode() {
                return OPCODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.PolyPointRequest readPolyPointRequest(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                int javaStart = 1;
                com.github.moaxcp.x11client.protocol.xproto.CoordModeEnum coordinateMode = com.github.moaxcp.x11client.protocol.xproto.CoordModeEnum.getByCode(in.readByte());
                javaStart += 1;
                short length = in.readCard16();
                javaStart += 2;
                int drawable = in.readCard32();
                javaStart += 4;
                int gc = in.readCard32();
                javaStart += 4;
                java.util.List<com.github.moaxcp.x11client.protocol.xproto.PointStruct> points = new java.util.ArrayList<>(length - javaStart);
                while(javaStart < Short.toUnsignedInt(length) * 4) {
                  com.github.moaxcp.x11client.protocol.xproto.PointStruct baseObject = com.github.moaxcp.x11client.protocol.xproto.PointStruct.readPointStruct(in);
                  points.add(baseObject);
                  javaStart += baseObject.getSize();
                }
                com.github.moaxcp.x11client.protocol.xproto.PolyPointRequest javaObject = new com.github.moaxcp.x11client.protocol.xproto.PolyPointRequest();
                javaObject.setCoordinateMode(coordinateMode);
                javaObject.setDrawable(drawable);
                javaObject.setGc(gc);
                javaObject.setPoints(points);
                in.readPadAlign(javaObject.getSize());
                return javaObject;
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(OPCODE);
                out.writeByte((byte) coordinateMode.getValue());
                out.writeCard16((short) getLength());
                out.writeCard32(drawable);
                out.writeCard32(gc);
                for(com.github.moaxcp.x11client.protocol.xproto.PointStruct t : points) {
                  t.write(out);
                }
                out.writePadAlign(getSize());
              }
            
              @java.lang.Override
              public int getSize() {
                return 1 + 1 + 2 + 4 + 4 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(points);
              }
            }
        '''.stripIndent()
    }

    def queryTextExtends() {
        given:
        xmlBuilder.xcb() {
            struct(name:'CHAR2B') {
                field(type:'CARD8', name:'byte1')
                field(type:'CARD8', name:'byte2')
            }
            xidtype(name:'FONT')
            xidtype(name:'GCONTEXT')
            xidunion(name:'FONTABLE') {
                type('FONT')
                type('GCONTEXT')
            }
            request(name:'QueryTextExtends', opcode:'48') {
                exprfield(type:'BOOL', name:'odd_length') {
                    op(op:'&') {
                        fieldref('string_len')
                        value('1')
                    }
                }
                field(type:'FONTABLE', name:'font')
                list(type:'CHAR2B', name:'string')
            }
        }

        when:
        addChildNodes()
        XTypeRequest request = result.resolveXType('QueryTextExtends')
        JavaRequest javaRequest = request.javaType

        then:
        javaRequest.typeSpec.toString() == '''\
            @lombok.Data
            @lombok.AllArgsConstructor
            @lombok.NoArgsConstructor
            public class QueryTextExtendsRequest implements com.github.moaxcp.x11client.protocol.XRequest {
              public static final byte OPCODE = 48;
            
              private int font;
            
              private java.util.List<com.github.moaxcp.x11client.protocol.xproto.Char2bStruct> string;
            
              public java.util.Optional<com.github.moaxcp.x11client.protocol.XReplySupplier> getReplySupplier(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                return Optional.empty();
              }
            
              public byte getOpCode() {
                return OPCODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.QueryTextExtendsRequest readQueryTextExtendsRequest(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                int javaStart = 1;
                boolean oddLength = in.readBool();
                javaStart += 1;
                short length = in.readCard16();
                javaStart += 2;
                int font = in.readCard32();
                javaStart += 4;
                java.util.List<com.github.moaxcp.x11client.protocol.xproto.Char2bStruct> string = new java.util.ArrayList<>(length - javaStart);
                while(javaStart < Short.toUnsignedInt(length) * 4) {
                  com.github.moaxcp.x11client.protocol.xproto.Char2bStruct baseObject = com.github.moaxcp.x11client.protocol.xproto.Char2bStruct.readChar2bStruct(in);
                  string.add(baseObject);
                  javaStart += baseObject.getSize();
                }
                com.github.moaxcp.x11client.protocol.xproto.QueryTextExtendsRequest javaObject = new com.github.moaxcp.x11client.protocol.xproto.QueryTextExtendsRequest();
                javaObject.setFont(font);
                javaObject.setString(string);
                in.readPadAlign(javaObject.getSize());
                return javaObject;
              }
            
              @java.lang.Override
              public void write(com.github.moaxcp.x11client.protocol.X11Output out) throws java.io.IOException {
                out.writeCard8(OPCODE);
                out.writeBool(getOddLength());
                out.writeCard16((short) getLength());
                out.writeCard32(font);
                for(com.github.moaxcp.x11client.protocol.xproto.Char2bStruct t : string) {
                  t.write(out);
                }
                out.writePadAlign(getSize());
              }
              
              public boolean getOddLength() {
                return ((string.size()) & (1)) > 0;
              }
            
              @java.lang.Override
              public int getSize() {
                return 1 + 1 + 2 + 4 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(string);
              }
            }
        '''.stripIndent()
    }
}
