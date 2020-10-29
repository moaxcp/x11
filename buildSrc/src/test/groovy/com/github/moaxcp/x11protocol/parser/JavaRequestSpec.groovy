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
              
              public java.util.Optional<com.github.moaxcp.x11client.protocol.XReplyFunction> getReplyFunction(
                  ) {
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
              public void write(byte offset, com.github.moaxcp.x11client.protocol.X11Output out) throws
                  java.io.IOException {
                out.writeCard8((byte)(java.lang.Byte.toUnsignedInt(OPCODE) + java.lang.Byte.toUnsignedInt(offset)));
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
              
              public java.util.Optional<com.github.moaxcp.x11client.protocol.XReplyFunction> getReplyFunction(
                  ) {
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
              public void write(byte offset, com.github.moaxcp.x11client.protocol.X11Output out) throws
                  java.io.IOException {
                out.writeCard8((byte)(java.lang.Byte.toUnsignedInt(OPCODE) + java.lang.Byte.toUnsignedInt(offset)));
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
            
              public java.util.Optional<com.github.moaxcp.x11client.protocol.XReplyFunction> getReplyFunction(
                  ) {
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
              public void write(byte offset, com.github.moaxcp.x11client.protocol.X11Output out) throws
                  java.io.IOException {
                out.writeCard8((byte)(java.lang.Byte.toUnsignedInt(OPCODE) + java.lang.Byte.toUnsignedInt(offset)));
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

    def bigRequestEnable() {
        given:
        xmlBuilder.xcb(header:'bigreq') {
            request(name:'Enable', opcode:'0') {
                reply {
                    pad(bytes:'1')
                    field(type:'CARD32', name:'maximum_request_length')
                }
            }
        }

        when:
        addChildNodes()
        XTypeRequest request = result.resolveXType('Enable')
        JavaRequest javaRequest = request.javaType

        then:
        javaRequest.typeSpec.toString() == '''\
            public class EnableRequest implements com.github.moaxcp.x11client.protocol.XRequest {
              public static final byte OPCODE = 0;
            
              public java.util.Optional<com.github.moaxcp.x11client.protocol.XReplyFunction> getReplyFunction(
                  ) {
                return Optional.of((field, sequenceNumber, in) -> com.github.moaxcp.x11client.protocol.xproto.EnableReply.readEnableReply(field, sequenceNumber, in));
              }
            
              public byte getOpCode() {
                return OPCODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.EnableRequest readEnableRequest(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                in.readByte();
                short length = in.readCard16();
                com.github.moaxcp.x11client.protocol.xproto.EnableRequest javaObject = new com.github.moaxcp.x11client.protocol.xproto.EnableRequest();
                return javaObject;
              }
            
              @java.lang.Override
              public void write(byte offset, com.github.moaxcp.x11client.protocol.X11Output out) throws
                  java.io.IOException {
                out.writeCard8((byte)(java.lang.Byte.toUnsignedInt(OPCODE) + java.lang.Byte.toUnsignedInt(offset)));
                out.writePad(1);
                out.writeCard16((short) 1);
              }
            
              @java.lang.Override
              public int getSize() {
                return 4;
              }
            }
        '''.stripIndent()
    }

    def createWindow() {
        given:
        xmlBuilder.xcb() {
            xidtype(name:'WINDOW')
            xidtype(name:'PIXMAP')
            xidtype(name:'CURSOR')
            typedef(oldname:'CARD32', newname:'VISUALID')
            'enum'(name:'WindowClass') {
                item(name:'CopyFromParent') {
                    value('0')
                }
                item(name:'InputOutput') {
                    value('1')
                }
                item(name:'InputOnly') {
                    value('2')
                }
            }
            'enum'(name:'CW') {
                item(name:'BackPixmap') {
                    bit('0')
                }
                item(name:'BackPixel') {
                    bit('1')
                }
                item(name:'BorderPixmap') {
                    bit('2')
                }
                item(name:'BorderPixel') {
                    bit('3')
                }
                item(name:'BitGravity') {
                    bit('4')
                }
                item(name:'WinGravity') {
                    bit('5')
                }
                item(name:'BackingStore') {
                    bit('6')
                }
                item(name:'BackingPlanes') {
                    bit('7')
                }
                item(name:'BackingPixel') {
                    bit('8')
                }
                item(name:'OverrideRedirect') {
                    bit('9')
                }
                item(name:'SaveUnder') {
                    bit('10')
                }
                item(name:'EventMask') {
                    bit('11')
                }
                item(name:'DontPropagate') {
                    bit('12')
                }
                item(name:'Colormap') {
                    bit('13')
                }
                item(name:'Cursor') {
                    bit('14')
                }
            }
            request(name:'CreateWindow', opcode:'1') {
                field(type:'CARD8', name:'depth')
                field(type:'WINDOW', name:'wid')
                field(type:'WINDOW', name:'parent')
                field(type:'INT16', name:'x')
                field(type:'CARD16', name:'y')
                field(type:'CARD16', name:'width')
                field(type:'CARD16', name:'height')
                field(type:'CARD16', name:'border_width')
                field(type:'CARD16', name:'class', 'enum':"WindowClass")
                field(type:'VISUALID', name:'visual')
                field(type:'CARD32', name:'value_mask', mask:'CW')
                'switch'(name:'value_list') {
                    fieldref('value_mask')
                    bitcase {
                        enumref(ref:'CW', 'BackPixmap')
                        field(type:'PIXMAP', name:'background_pixmap', altenum:'BackPixmap')
                    }
                    bitcase {
                        enumref(ref:'CW', 'BackPixel')
                        field(type:'CARD32', name:'background_pixel')
                    }
                    bitcase {
                        enumref(ref:'CW', 'BorderPixmap')
                        field(type:'PIXMAP', name:'border_pixmap', altenum:'Pixmap')
                    }
                    bitcase {
                        enumref(ref:'CW', 'BorderPixel')
                        field(type:'CARD32', name:'border_pixel')
                    }
                    bitcase {
                        enumref(ref:'CW', 'BitGravity')
                        field(type:'CARD32', name:'bit_gravity', enum:'Gravity')
                    }
                    bitcase {
                        enumref(ref:'CW', 'WinGravity')
                        field(type:'CARD32', name:'win_gravity', enum:'Gravity')
                    }
                    bitcase {
                        enumref(ref:'CW', 'BackingStore')
                        field(type:'CARD32', name:'backing_store', enum:'BackingStore')
                    }
                    bitcase {
                        enumref(ref:'CW', 'BackingPlanes')
                        field(type:'CARD32', name:'backing_planes')
                    }
                    bitcase {
                        enumref(ref:'CW', 'BackingPixel')
                        field(type:'CARD32', name:'backing_pixel')
                    }
                    bitcase {
                        enumref(ref:'CW', 'OverrideRedirect')
                        field(type:'BOOL32', name:'override_redirect')
                    }
                    bitcase {
                        enumref(ref:'CW', 'SaveUnder')
                        field(type:'BOOL32', name:'save_under')
                    }
                    bitcase {
                        enumref(ref:'CW', 'EventMask')
                        field(type:'CARD32', name:'event_mask', mask:'EventMask')
                    }
                    bitcase {
                        enumref(ref:'CW', 'DontPropagate')
                        field(type:'CARD32', name:'do_not_propogate_mask', mask:'EventMask')
                    }
                    bitcase {
                        enumref(ref:'CW', 'Colormap')
                        field(type:'COLORMAP', name:'colormap', altenum:'Colormap')
                    }
                    bitcase {
                        enumref(ref:'CW', 'Cursor')
                        field(type:'CURSOR', name:'cursor', altenum:'Cursor')
                    }
                }
            }
        }

        when:
        addChildNodes()
        XTypeRequest request = result.resolveXType('CreateWindow')
        JavaRequest javaRequest = request.javaType

        then:
        javaRequest.typeSpec.toString() == '''\
            @lombok.Data
            @lombok.AllArgsConstructor
            @lombok.NoArgsConstructor
            public class CreateWindowRequest implements com.github.moaxcp.x11client.protocol.XRequest {
              public static final byte OPCODE = 1;
            
              private byte depth;
            
              private int wid;
            
              private int parent;
            
              private short x;
            
              private short y;
            
              private short width;
            
              private short height;
            
              private short borderWidth;
            
              private com.github.moaxcp.x11client.protocol.xproto.WindowClassEnum clazz;
            
              private int visual;
            
              private int valueMask;
            
              public java.util.Optional<com.github.moaxcp.x11client.protocol.XReplyFunction> getReplyFunction(
                  ) {
                return Optional.empty();
              }
            
              public byte getOpCode() {
                return OPCODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest readCreateWindowRequest(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                byte depth = in.readCard8();
                short length = in.readCard16();
                int wid = in.readCard32();
                int parent = in.readCard32();
                short x = in.readInt16();
                short y = in.readCard16();
                short width = in.readCard16();
                short height = in.readCard16();
                short borderWidth = in.readCard16();
                com.github.moaxcp.x11client.protocol.xproto.WindowClassEnum clazz = com.github.moaxcp.x11client.protocol.xproto.WindowClassEnum.getByCode(in.readCard16());
                int visual = in.readCard32();
                int valueMask = in.readCard32();
                com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest javaObject = new com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest();
                javaObject.setDepth(depth);
                javaObject.setWid(wid);
                javaObject.setParent(parent);
                javaObject.setX(x);
                javaObject.setY(y);
                javaObject.setWidth(width);
                javaObject.setHeight(height);
                javaObject.setBorderWidth(borderWidth);
                javaObject.setClazz(clazz);
                javaObject.setVisual(visual);
                javaObject.setValueMask(valueMask);
                return javaObject;
              }
            
              @java.lang.Override
              public void write(byte offset, com.github.moaxcp.x11client.protocol.X11Output out) throws
                  java.io.IOException {
                out.writeCard8((byte)(java.lang.Byte.toUnsignedInt(OPCODE) + java.lang.Byte.toUnsignedInt(offset)));
                out.writeCard8(depth);
                out.writeCard16((short) getLength());
                out.writeCard32(wid);
                out.writeCard32(parent);
                out.writeInt16(x);
                out.writeCard16(y);
                out.writeCard16(width);
                out.writeCard16(height);
                out.writeCard16(borderWidth);
                out.writeCard16((short) clazz.getValue());
                out.writeCard32(visual);
                out.writeCard32(valueMask);
              }
            
              public void valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum maskEnum) {
                valueMask = (int) maskEnum.enableFor(valueMask);
              }
            
              public void valueMaskDisable(com.github.moaxcp.x11client.protocol.xproto.CwEnum maskEnum) {
                valueMask = (int) maskEnum.disableFor(valueMask);
              }
            
              @java.lang.Override
              public int getSize() {
                return 1 + 1 + 2 + 4 + 4 + 2 + 2 + 2 + 2 + 2 + 2 + 4 + 4;
              }
            }
        '''.stripIndent()
    }
}
