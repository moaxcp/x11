package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec
import groovy.xml.MarkupBuilder
import spock.lang.Ignore

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
            @lombok.Value
            @lombok.Builder
            public class DestroyWindow implements com.github.moaxcp.x11client.protocol.OneWayRequest, com.github.moaxcp.x11client.protocol.xproto.XprotoObject {
              public static final byte OPCODE = 4;
            
              private int window;
            
              public byte getOpCode() {
                return OPCODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.DestroyWindow readDestroyWindow(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                in.readPad(1);
                short length = in.readCard16();
                int window = in.readCard32();
                com.github.moaxcp.x11client.protocol.xproto.DestroyWindow.DestroyWindowBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.DestroyWindow.builder();
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
            
              public static class DestroyWindowBuilder {
                public int getSize() {
                  return 8;
                }
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
            @lombok.Value
            @lombok.Builder
            public class PolyPoint implements com.github.moaxcp.x11client.protocol.OneWayRequest, com.github.moaxcp.x11client.protocol.xproto.XprotoObject {
              public static final byte OPCODE = 64;
            
              private byte coordinateMode;
            
              private int drawable;
            
              private int gc;
            
              @lombok.NonNull
              private java.util.List<com.github.moaxcp.x11client.protocol.xproto.Point> points;
            
              public byte getOpCode() {
                return OPCODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.PolyPoint readPolyPoint(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                int javaStart = 1;
                byte coordinateMode = in.readByte();
                javaStart += 1;
                short length = in.readCard16();
                javaStart += 2;
                int drawable = in.readCard32();
                javaStart += 4;
                int gc = in.readCard32();
                javaStart += 4;
                java.util.List<com.github.moaxcp.x11client.protocol.xproto.Point> points = new java.util.ArrayList<>(length - javaStart);
                while(javaStart < Short.toUnsignedInt(length) * 4) {
                  com.github.moaxcp.x11client.protocol.xproto.Point baseObject = com.github.moaxcp.x11client.protocol.xproto.Point.readPoint(in);
                  points.add(baseObject);
                  javaStart += baseObject.getSize();
                }
                com.github.moaxcp.x11client.protocol.xproto.PolyPoint.PolyPointBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.PolyPoint.builder();
                javaBuilder.coordinateMode(coordinateMode);
                javaBuilder.drawable(drawable);
                javaBuilder.gc(gc);
                javaBuilder.points(points);
                in.readPadAlign(javaBuilder.getSize());
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(byte offset, com.github.moaxcp.x11client.protocol.X11Output out) throws
                  java.io.IOException {
                out.writeCard8((byte)(java.lang.Byte.toUnsignedInt(OPCODE) + java.lang.Byte.toUnsignedInt(offset)));
                out.writeByte(coordinateMode);
                out.writeCard16((short) getLength());
                out.writeCard32(drawable);
                out.writeCard32(gc);
                for(com.github.moaxcp.x11client.protocol.xproto.Point t : points) {
                  t.write(out);
                }
                out.writePadAlign(getSize());
              }
            
              @java.lang.Override
              public int getSize() {
                return 12 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(points);
              }
            
              public static class PolyPointBuilder {
                public com.github.moaxcp.x11client.protocol.xproto.PolyPoint.PolyPointBuilder coordinateMode(
                    com.github.moaxcp.x11client.protocol.xproto.CoordMode coordinateMode) {
                  this.coordinateMode = (byte) coordinateMode.getValue();
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.PolyPoint.PolyPointBuilder coordinateMode(
                    byte coordinateMode) {
                  this.coordinateMode = coordinateMode;
                  return this;
                }
            
                public int getSize() {
                  return 12 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(points);
                }
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
            @lombok.Value
            @lombok.Builder
            public class QueryTextExtends implements com.github.moaxcp.x11client.protocol.OneWayRequest, com.github.moaxcp.x11client.protocol.xproto.XprotoObject {
              public static final byte OPCODE = 48;
            
              private int font;
            
              @lombok.NonNull
              private java.util.List<com.github.moaxcp.x11client.protocol.xproto.Char2b> string;
            
              public byte getOpCode() {
                return OPCODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.QueryTextExtends readQueryTextExtends(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                int javaStart = 1;
                boolean oddLength = in.readBool();
                javaStart += 1;
                short length = in.readCard16();
                javaStart += 2;
                int font = in.readCard32();
                javaStart += 4;
                java.util.List<com.github.moaxcp.x11client.protocol.xproto.Char2b> string = new java.util.ArrayList<>(length - javaStart);
                while(javaStart < Short.toUnsignedInt(length) * 4) {
                  com.github.moaxcp.x11client.protocol.xproto.Char2b baseObject = com.github.moaxcp.x11client.protocol.xproto.Char2b.readChar2b(in);
                  string.add(baseObject);
                  javaStart += baseObject.getSize();
                }
                com.github.moaxcp.x11client.protocol.xproto.QueryTextExtends.QueryTextExtendsBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.QueryTextExtends.builder();
                javaBuilder.font(font);
                javaBuilder.string(string);
                in.readPadAlign(javaBuilder.getSize());
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(byte offset, com.github.moaxcp.x11client.protocol.X11Output out) throws
                  java.io.IOException {
                out.writeCard8((byte)(java.lang.Byte.toUnsignedInt(OPCODE) + java.lang.Byte.toUnsignedInt(offset)));
                out.writeBool(getOddLength());
                out.writeCard16((short) getLength());
                out.writeCard32(font);
                for(com.github.moaxcp.x11client.protocol.xproto.Char2b t : string) {
                  t.write(out);
                }
                out.writePadAlign(getSize());
              }
            
              public boolean getOddLength() {
                return ((string.size()) & (1)) > 0;
              }
            
              @java.lang.Override
              public int getSize() {
                return 8 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(string);
              }
            
              public static class QueryTextExtendsBuilder {
                public int getSize() {
                  return 8 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(string);
                }
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
            @lombok.Value
            @lombok.Builder
            public class Enable implements com.github.moaxcp.x11client.protocol.TwoWayRequest<com.github.moaxcp.x11client.protocol.xproto.EnableReply>, com.github.moaxcp.x11client.protocol.xproto.XprotoObject {
              public static final byte OPCODE = 0;
            
              public com.github.moaxcp.x11client.protocol.XReplyFunction<com.github.moaxcp.x11client.protocol.xproto.EnableReply> getReplyFunction(
                  ) {
                return (field, sequenceNumber, in) -> com.github.moaxcp.x11client.protocol.xproto.EnableReply.readEnableReply(field, sequenceNumber, in);
              }
            
              public byte getOpCode() {
                return OPCODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.Enable readEnable(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                in.readPad(1);
                short length = in.readCard16();
                com.github.moaxcp.x11client.protocol.xproto.Enable.EnableBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.Enable.builder();
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(byte offset, com.github.moaxcp.x11client.protocol.X11Output out) throws
                  java.io.IOException {
                out.writeCard8((byte)(java.lang.Byte.toUnsignedInt(OPCODE) + java.lang.Byte.toUnsignedInt(offset)));
                out.writePad(1);
                out.writeCard16((short) getLength());
              }
            
              @java.lang.Override
              public int getSize() {
                return 4;
              }
            
              public static class EnableBuilder {
                public int getSize() {
                  return 4;
                }
              }
            }
        '''.stripIndent()
    }

    @Ignore
    def createWindow() {
        given:
        xmlBuilder.xcb() {
            xidtype(name:'WINDOW')
            xidtype(name:'PIXMAP')
            xidtype(name:'CURSOR')
            xidtype(name:'COLORMAP')
            typedef(oldname:'CARD32', newname:'VISUALID')
            typedef(oldname:'CARD32', newname:'BOOL32')
            'enum'(name:'Gravity') {
                item(name:'BitForget') {
                    value('0')
                }
            }
            'enum'(name:'BackingStore') {
                item(name:'NotUseful') {
                    value('0')
                }
            }
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
            'enum'(name:'EventMask') {
                item(name:'NoEvent') {
                    value('0')
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
            @lombok.Value
            @lombok.Builder
            public class CreateWindow implements com.github.moaxcp.x11client.protocol.OneWayRequest, com.github.moaxcp.x11client.protocol.xproto.XprotoObject {
              public static final byte OPCODE = 1;
            
              private byte depth;
            
              private int wid;
            
              private int parent;
            
              private short x;
            
              private short y;
            
              private short width;
            
              private short height;
            
              private short borderWidth;
            
              private short clazz;
            
              private int visual;
            
              private int valueMask;
            
              private int backgroundPixmap;
            
              private int backgroundPixel;
            
              private int borderPixmap;
            
              private int borderPixel;
            
              private int bitGravity;
            
              private int winGravity;
            
              private int backingStore;
            
              private int backingPlanes;
            
              private int backingPixel;
            
              private int overrideRedirect;
            
              private int saveUnder;
            
              private int eventMask;
            
              private int doNotPropogateMask;
            
              private int colormap;
            
              private int cursor;
            
              public byte getOpCode() {
                return OPCODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.CreateWindow readCreateWindow(
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
                short clazz = in.readCard16();
                int visual = in.readCard32();
                int valueMask = in.readCard32();
                com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.CreateWindow.builder();
                javaBuilder.depth(depth);
                javaBuilder.wid(wid);
                javaBuilder.parent(parent);
                javaBuilder.x(x);
                javaBuilder.y(y);
                javaBuilder.width(width);
                javaBuilder.height(height);
                javaBuilder.borderWidth(borderWidth);
                javaBuilder.clazz(clazz);
                javaBuilder.visual(visual);
                javaBuilder.valueMask(valueMask);
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.BACK_PIXMAP.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  javaBuilder.backgroundPixmap(in.readCard32());
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.BACK_PIXEL.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  javaBuilder.backgroundPixel(in.readCard32());
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.BORDER_PIXMAP.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  javaBuilder.borderPixmap(in.readCard32());
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.BORDER_PIXEL.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  javaBuilder.borderPixel(in.readCard32());
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.BIT_GRAVITY.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  javaBuilder.bitGravity(in.readCard32());
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.WIN_GRAVITY.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  javaBuilder.winGravity(in.readCard32());
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.BACKING_STORE.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  javaBuilder.backingStore(in.readCard32());
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.BACKING_PLANES.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  javaBuilder.backingPlanes(in.readCard32());
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.BACKING_PIXEL.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  javaBuilder.backingPixel(in.readCard32());
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.OVERRIDE_REDIRECT.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  javaBuilder.overrideRedirect(in.readCard32());
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.SAVE_UNDER.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  javaBuilder.saveUnder(in.readCard32());
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.EVENT_MASK.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  javaBuilder.eventMask(in.readCard32());
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.DONT_PROPAGATE.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  javaBuilder.doNotPropogateMask(in.readCard32());
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.COLORMAP.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  javaBuilder.colormap(in.readCard32());
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.CURSOR.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  javaBuilder.cursor(in.readCard32());
                }
                in.readPadAlign(javaBuilder.getSize());
                return javaBuilder.build();
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
                out.writeCard16(clazz);
                out.writeCard32(visual);
                out.writeCard32(valueMask);
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.BACK_PIXMAP.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  out.writeCard32(backgroundPixmap);
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.BACK_PIXEL.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  out.writeCard32(backgroundPixel);
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.BORDER_PIXMAP.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  out.writeCard32(borderPixmap);
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.BORDER_PIXEL.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  out.writeCard32(borderPixel);
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.BIT_GRAVITY.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  out.writeCard32(bitGravity);
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.WIN_GRAVITY.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  out.writeCard32(winGravity);
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.BACKING_STORE.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  out.writeCard32(backingStore);
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.BACKING_PLANES.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  out.writeCard32(backingPlanes);
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.BACKING_PIXEL.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  out.writeCard32(backingPixel);
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.OVERRIDE_REDIRECT.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  out.writeCard32(overrideRedirect);
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.SAVE_UNDER.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  out.writeCard32(saveUnder);
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.EVENT_MASK.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  out.writeCard32(eventMask);
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.DONT_PROPAGATE.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  out.writeCard32(doNotPropogateMask);
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.COLORMAP.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  out.writeCard32(colormap);
                }
                if(com.github.moaxcp.x11client.protocol.xproto.Cw.CURSOR.enabledFor((int) (Integer.toUnsignedLong(valueMask)))) {
                  out.writeCard32(cursor);
                }
                out.writePadAlign(getSize());
              }
            
              public boolean isValueMaskEnabled(
                  @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.Cw... maskEnums) {
                for(com.github.moaxcp.x11client.protocol.xproto.Cw m : maskEnums) {
                  if(!m.isEnabled(valueMask)) {
                    return false;
                  }
                }
                return true;
              }
            
              public boolean isEventMaskEnabled(
                  @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.EventMask... maskEnums) {
                for(com.github.moaxcp.x11client.protocol.xproto.EventMask m : maskEnums) {
                  if(!m.isEnabled(eventMask)) {
                    return false;
                  }
                }
                return true;
              }
            
              public boolean isDoNotPropogateMaskEnabled(
                  @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.EventMask... maskEnums) {
                for(com.github.moaxcp.x11client.protocol.xproto.EventMask m : maskEnums) {
                  if(!m.isEnabled(doNotPropogateMask)) {
                    return false;
                  }
                }
                return true;
              }
            
              @java.lang.Override
              public int getSize() {
                return 32 + (com.github.moaxcp.x11client.protocol.xproto.Cw.BACK_PIXMAP.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.BACK_PIXEL.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.BORDER_PIXMAP.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.BORDER_PIXEL.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.BIT_GRAVITY.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.WIN_GRAVITY.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.BACKING_STORE.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.BACKING_PLANES.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.BACKING_PIXEL.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.OVERRIDE_REDIRECT.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.SAVE_UNDER.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.EVENT_MASK.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.DONT_PROPAGATE.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.COLORMAP.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.CURSOR.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
              }
            
              public static class CreateWindowBuilder {
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder clazz(
                    com.github.moaxcp.x11client.protocol.xproto.WindowClass clazz) {
                  this.clazz = (short) clazz.getValue();
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder clazz(
                    short clazz) {
                  this.clazz = clazz;
                  return this;
                }
            
                private com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder valueMask(
                    int valueMask) {
                  this.valueMask = valueMask;
                  return this;
                }
            
                public boolean isValueMaskEnabled(
                    @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.Cw... maskEnums) {
                  for(com.github.moaxcp.x11client.protocol.xproto.Cw m : maskEnums) {
                    if(!m.isEnabled(valueMask)) {
                      return false;
                    }
                  }
                  return true;
                }
            
                private com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder valueMaskEnable(
                    com.github.moaxcp.x11client.protocol.xproto.Cw... maskEnums) {
                  for(com.github.moaxcp.x11client.protocol.xproto.Cw m : maskEnums) {
                    valueMask((int) m.enableFor(valueMask));
                  }
                  return this;
                }
            
                private com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder valueMaskDisable(
                    com.github.moaxcp.x11client.protocol.xproto.Cw... maskEnums) {
                  for(com.github.moaxcp.x11client.protocol.xproto.Cw m : maskEnums) {
                    valueMask((int) m.disableFor(valueMask));
                  }
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder backgroundPixmap(
                    int backgroundPixmap) {
                  this.backgroundPixmap = backgroundPixmap;
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.BACK_PIXMAP);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder backgroundPixel(
                    int backgroundPixel) {
                  this.backgroundPixel = backgroundPixel;
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.BACK_PIXEL);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder borderPixmap(
                    int borderPixmap) {
                  this.borderPixmap = borderPixmap;
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.BORDER_PIXMAP);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder borderPixel(
                    int borderPixel) {
                  this.borderPixel = borderPixel;
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.BORDER_PIXEL);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder bitGravity(
                    int bitGravity) {
                  this.bitGravity = bitGravity;
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.BIT_GRAVITY);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder bitGravity(
                    com.github.moaxcp.x11client.protocol.xproto.Gravity bitGravity) {
                  this.bitGravity = (int) bitGravity.getValue();
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.BIT_GRAVITY);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder winGravity(
                    int winGravity) {
                  this.winGravity = winGravity;
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.WIN_GRAVITY);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder winGravity(
                    com.github.moaxcp.x11client.protocol.xproto.Gravity winGravity) {
                  this.winGravity = (int) winGravity.getValue();
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.WIN_GRAVITY);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder backingStore(
                    int backingStore) {
                  this.backingStore = backingStore;
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.BACKING_STORE);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder backingStore(
                    com.github.moaxcp.x11client.protocol.xproto.BackingStore backingStore) {
                  this.backingStore = (int) backingStore.getValue();
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.BACKING_STORE);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder backingPlanes(
                    int backingPlanes) {
                  this.backingPlanes = backingPlanes;
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.BACKING_PLANES);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder backingPixel(
                    int backingPixel) {
                  this.backingPixel = backingPixel;
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.BACKING_PIXEL);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder overrideRedirect(
                    int overrideRedirect) {
                  this.overrideRedirect = overrideRedirect;
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.OVERRIDE_REDIRECT);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder saveUnder(
                    int saveUnder) {
                  this.saveUnder = saveUnder;
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.SAVE_UNDER);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder eventMask(
                    int eventMask) {
                  this.eventMask = eventMask;
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.EVENT_MASK);
                  return this;
                }
            
                public boolean isEventMaskEnabled(
                    @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.EventMask... maskEnums) {
                  for(com.github.moaxcp.x11client.protocol.xproto.EventMask m : maskEnums) {
                    if(!m.isEnabled(eventMask)) {
                      return false;
                    }
                  }
                  return true;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder eventMaskEnable(
                    com.github.moaxcp.x11client.protocol.xproto.EventMask... maskEnums) {
                  for(com.github.moaxcp.x11client.protocol.xproto.EventMask m : maskEnums) {
                    eventMask((int) m.enableFor(eventMask));
                  }
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder eventMaskDisable(
                    com.github.moaxcp.x11client.protocol.xproto.EventMask... maskEnums) {
                  for(com.github.moaxcp.x11client.protocol.xproto.EventMask m : maskEnums) {
                    eventMask((int) m.disableFor(eventMask));
                  }
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder doNotPropogateMask(
                    int doNotPropogateMask) {
                  this.doNotPropogateMask = doNotPropogateMask;
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.DONT_PROPAGATE);
                  return this;
                }
            
                public boolean isDoNotPropogateMaskEnabled(
                    @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.EventMask... maskEnums) {
                  for(com.github.moaxcp.x11client.protocol.xproto.EventMask m : maskEnums) {
                    if(!m.isEnabled(doNotPropogateMask)) {
                      return false;
                    }
                  }
                  return true;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder doNotPropogateMaskEnable(
                    com.github.moaxcp.x11client.protocol.xproto.EventMask... maskEnums) {
                  for(com.github.moaxcp.x11client.protocol.xproto.EventMask m : maskEnums) {
                    doNotPropogateMask((int) m.enableFor(doNotPropogateMask));
                  }
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder doNotPropogateMaskDisable(
                    com.github.moaxcp.x11client.protocol.xproto.EventMask... maskEnums) {
                  for(com.github.moaxcp.x11client.protocol.xproto.EventMask m : maskEnums) {
                    doNotPropogateMask((int) m.disableFor(doNotPropogateMask));
                  }
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder colormap(
                    int colormap) {
                  this.colormap = colormap;
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.COLORMAP);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindow.CreateWindowBuilder cursor(
                    int cursor) {
                  this.cursor = cursor;
                  com.github.moaxcp.x11protocol.xcbparser.expression.FieldRefExpression@c64e3eaaEnable(com.github.moaxcp.x11client.protocol.xproto.Cw.CURSOR);
                  return this;
                }
            
                public int getSize() {
                  return 32 + (com.github.moaxcp.x11client.protocol.xproto.Cw.BACK_PIXMAP.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.BACK_PIXEL.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.BORDER_PIXMAP.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.BORDER_PIXEL.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.BIT_GRAVITY.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.WIN_GRAVITY.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.BACKING_STORE.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.BACKING_PLANES.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.BACKING_PIXEL.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.OVERRIDE_REDIRECT.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.SAVE_UNDER.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.EVENT_MASK.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.DONT_PROPAGATE.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.COLORMAP.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (com.github.moaxcp.x11client.protocol.xproto.Cw.CURSOR.enabledFor((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
                }
              }
            }
        '''.stripIndent()
    }

    def await() {
        given:
        StringWriter syncString = new StringWriter()
        MarkupBuilder syncXml = new MarkupBuilder(syncString)
        syncXml.xcb() {
            struct(name: 'INT64') {
                field(type: 'INT32', name: 'hi')
                field(type: 'CARD32', name: 'lo')
            }
        }
        XResult syncResult = new XResult(basePackage: 'com.github.moaxcp.x11client.protocol', header:'sync')
        new XmlSlurper().parseText(syncString.toString()).nodeIterator().next().childNodes().each {
            syncResult.addNode(it)
        }
        result.imports.put('sync', syncResult)
        xmlBuilder.xcb() {
            struct(name:'TRIGGER') {
                field(type:'COUNTER', name:'counter')
                field(type:'CARD32', name:'wait_type', enum:'VALUETYPE')
                field(type:'sync:INT64', name:'wait_value')
                field(type:'CARD32', name:'test_type', enum:'TESTTYPE')
            }
            struct(name:'WAITCONDITION') {
                field(type:'TRIGGER', name:'trigger')
                field(type:'sync:INT64', name:'even_threshold')
            }
            request(name:'Await', opcode:'7') {
                list(type:'WAITCONDITION', name:'wait_list')
            }
        }

        when:
        addChildNodes()
        XTypeRequest request = result.resolveXType('Await')
        JavaRequest javaRequest = request.javaType

        then:
        javaRequest.typeSpec.toString() == '''\
            @lombok.Value
            @lombok.Builder
            public class Await implements com.github.moaxcp.x11client.protocol.OneWayRequest, com.github.moaxcp.x11client.protocol.xproto.XprotoObject {
              public static final byte OPCODE = 7;
            
              @lombok.NonNull
              private java.util.List<com.github.moaxcp.x11client.protocol.xproto.Waitcondition> waitList;
            
              public byte getOpCode() {
                return OPCODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.Await readAwait(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                int javaStart = 1;
                in.readPad(1);
                javaStart += 1;
                short length = in.readCard16();
                javaStart += 2;
                java.util.List<com.github.moaxcp.x11client.protocol.xproto.Waitcondition> waitList = new java.util.ArrayList<>(length - javaStart);
                while(javaStart < Short.toUnsignedInt(length) * 4) {
                  com.github.moaxcp.x11client.protocol.xproto.Waitcondition baseObject = com.github.moaxcp.x11client.protocol.xproto.Waitcondition.readWaitcondition(in);
                  waitList.add(baseObject);
                  javaStart += baseObject.getSize();
                }
                com.github.moaxcp.x11client.protocol.xproto.Await.AwaitBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.Await.builder();
                javaBuilder.waitList(waitList);
                in.readPadAlign(javaBuilder.getSize());
                return javaBuilder.build();
              }
            
              @java.lang.Override
              public void write(byte offset, com.github.moaxcp.x11client.protocol.X11Output out) throws
                  java.io.IOException {
                out.writeCard8((byte)(java.lang.Byte.toUnsignedInt(OPCODE) + java.lang.Byte.toUnsignedInt(offset)));
                out.writePad(1);
                out.writeCard16((short) getLength());
                for(com.github.moaxcp.x11client.protocol.xproto.Waitcondition t : waitList) {
                  t.write(out);
                }
                out.writePadAlign(getSize());
              }
            
              @java.lang.Override
              public int getSize() {
                return 4 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(waitList);
              }
            
              public static class AwaitBuilder {
                public int getSize() {
                  return 4 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(waitList);
                }
              }
            }
        '''.stripIndent()

    }
}
