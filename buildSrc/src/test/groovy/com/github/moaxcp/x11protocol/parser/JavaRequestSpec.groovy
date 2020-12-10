package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec
import com.google.common.truth.Truth

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
            public class DestroyWindowRequest implements com.github.moaxcp.x11client.protocol.OneWayRequest {
              public static final byte OPCODE = 4;
            
              private int window;
            
              public byte getOpCode() {
                return OPCODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.DestroyWindowRequest readDestroyWindowRequest(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                in.readPad(1);
                short length = in.readCard16();
                int window = in.readCard32();
                com.github.moaxcp.x11client.protocol.xproto.DestroyWindowRequest.DestroyWindowRequestBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.DestroyWindowRequest.builder();
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
            
              public static class DestroyWindowRequestBuilder {
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
            public class PolyPointRequest implements com.github.moaxcp.x11client.protocol.OneWayRequest {
              public static final byte OPCODE = 64;
            
              private byte coordinateMode;
            
              private int drawable;
            
              private int gc;
            
              @lombok.NonNull
              private java.util.List<com.github.moaxcp.x11client.protocol.xproto.PointStruct> points;
            
              public byte getOpCode() {
                return OPCODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.PolyPointRequest readPolyPointRequest(
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
                java.util.List<com.github.moaxcp.x11client.protocol.xproto.PointStruct> points = new java.util.ArrayList<>(length - javaStart);
                while(javaStart < Short.toUnsignedInt(length) * 4) {
                  com.github.moaxcp.x11client.protocol.xproto.PointStruct baseObject = com.github.moaxcp.x11client.protocol.xproto.PointStruct.readPointStruct(in);
                  points.add(baseObject);
                  javaStart += baseObject.getSize();
                }
                com.github.moaxcp.x11client.protocol.xproto.PolyPointRequest.PolyPointRequestBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.PolyPointRequest.builder();
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
                for(com.github.moaxcp.x11client.protocol.xproto.PointStruct t : points) {
                  t.write(out);
                }
                out.writePadAlign(getSize());
              }
            
              @java.lang.Override
              public int getSize() {
                return 12 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(points);
              }
            
              public static class PolyPointRequestBuilder {
                public com.github.moaxcp.x11client.protocol.xproto.PolyPointRequest.PolyPointRequestBuilder coordinateMode(
                    com.github.moaxcp.x11client.protocol.xproto.CoordModeEnum coordinateMode) {
                  this.coordinateMode = (byte) coordinateMode.getValue();
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.PolyPointRequest.PolyPointRequestBuilder coordinateMode(
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
            public class QueryTextExtendsRequest implements com.github.moaxcp.x11client.protocol.OneWayRequest {
              public static final byte OPCODE = 48;
            
              private int font;
            
              @lombok.NonNull
              private java.util.List<com.github.moaxcp.x11client.protocol.xproto.Char2bStruct> string;
            
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
                com.github.moaxcp.x11client.protocol.xproto.QueryTextExtendsRequest.QueryTextExtendsRequestBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.QueryTextExtendsRequest.builder();
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
                return 8 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(string);
              }
            
              public static class QueryTextExtendsRequestBuilder {
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
            public class EnableRequest implements com.github.moaxcp.x11client.protocol.TwoWayRequest<com.github.moaxcp.x11client.protocol.xproto.EnableReply> {
              public static final byte OPCODE = 0;
            
              public com.github.moaxcp.x11client.protocol.XReplyFunction<com.github.moaxcp.x11client.protocol.xproto.EnableReply> getReplyFunction(
                  ) {
                return (field, sequenceNumber, in) -> com.github.moaxcp.x11client.protocol.xproto.EnableReply.readEnableReply(field, sequenceNumber, in);
              }
            
              public byte getOpCode() {
                return OPCODE;
              }
            
              public static com.github.moaxcp.x11client.protocol.xproto.EnableRequest readEnableRequest(
                  com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
                in.readPad(1);
                short length = in.readCard16();
                com.github.moaxcp.x11client.protocol.xproto.EnableRequest.EnableRequestBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.EnableRequest.builder();
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
            
              public static class EnableRequestBuilder {
                public int getSize() {
                  return 4;
                }
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
        Truth.assertThat(javaRequest.typeSpec.toString()).isEqualTo '''\
            @lombok.Value
            @lombok.Builder
            public class CreateWindowRequest implements com.github.moaxcp.x11client.protocol.OneWayRequest {
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
                short clazz = in.readCard16();
                int visual = in.readCard32();
                int valueMask = in.readCard32();
                com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.builder();
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
                if(javaBuilder.isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACK_PIXMAP)) {
                  javaBuilder.backgroundPixmap(in.readCard32());
                }
                if(javaBuilder.isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACK_PIXEL)) {
                  javaBuilder.backgroundPixel(in.readCard32());
                }
                if(javaBuilder.isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BORDER_PIXMAP)) {
                  javaBuilder.borderPixmap(in.readCard32());
                }
                if(javaBuilder.isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BORDER_PIXEL)) {
                  javaBuilder.borderPixel(in.readCard32());
                }
                if(javaBuilder.isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BIT_GRAVITY)) {
                  javaBuilder.bitGravity(in.readCard32());
                }
                if(javaBuilder.isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.WIN_GRAVITY)) {
                  javaBuilder.winGravity(in.readCard32());
                }
                if(javaBuilder.isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACKING_STORE)) {
                  javaBuilder.backingStore(in.readCard32());
                }
                if(javaBuilder.isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACKING_PLANES)) {
                  javaBuilder.backingPlanes(in.readCard32());
                }
                if(javaBuilder.isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACKING_PIXEL)) {
                  javaBuilder.backingPixel(in.readCard32());
                }
                if(javaBuilder.isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.OVERRIDE_REDIRECT)) {
                  javaBuilder.overrideRedirect(in.readCard32());
                }
                if(javaBuilder.isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.SAVE_UNDER)) {
                  javaBuilder.saveUnder(in.readCard32());
                }
                if(javaBuilder.isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.EVENT_MASK)) {
                  javaBuilder.eventMask(in.readCard32());
                }
                if(javaBuilder.isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.DONT_PROPAGATE)) {
                  javaBuilder.doNotPropogateMask(in.readCard32());
                }
                if(javaBuilder.isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.COLORMAP)) {
                  javaBuilder.colormap(in.readCard32());
                }
                if(javaBuilder.isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.CURSOR)) {
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
                if(isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACK_PIXMAP)) {
                  out.writeCard32(backgroundPixmap);
                }
                if(isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACK_PIXEL)) {
                  out.writeCard32(backgroundPixel);
                }
                if(isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BORDER_PIXMAP)) {
                  out.writeCard32(borderPixmap);
                }
                if(isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BORDER_PIXEL)) {
                  out.writeCard32(borderPixel);
                }
                if(isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BIT_GRAVITY)) {
                  out.writeCard32(bitGravity);
                }
                if(isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.WIN_GRAVITY)) {
                  out.writeCard32(winGravity);
                }
                if(isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACKING_STORE)) {
                  out.writeCard32(backingStore);
                }
                if(isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACKING_PLANES)) {
                  out.writeCard32(backingPlanes);
                }
                if(isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACKING_PIXEL)) {
                  out.writeCard32(backingPixel);
                }
                if(isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.OVERRIDE_REDIRECT)) {
                  out.writeCard32(overrideRedirect);
                }
                if(isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.SAVE_UNDER)) {
                  out.writeCard32(saveUnder);
                }
                if(isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.EVENT_MASK)) {
                  out.writeCard32(eventMask);
                }
                if(isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.DONT_PROPAGATE)) {
                  out.writeCard32(doNotPropogateMask);
                }
                if(isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.COLORMAP)) {
                  out.writeCard32(colormap);
                }
                if(isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.CURSOR)) {
                  out.writeCard32(cursor);
                }
                out.writePadAlign(getSize());
              }
            
              public boolean isValueMaskEnabled(
                  @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.CwEnum maskEnum,
                  @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.CwEnum... maskEnums) {
                boolean enabled = maskEnum.isEnabled(valueMask);
                if(!enabled) {
                  return false;
                }
                for(com.github.moaxcp.x11client.protocol.xproto.CwEnum m : maskEnums) {
                  java.util.Objects.requireNonNull(m, "maskEnums must not contain null");
                  enabled &= m.isEnabled(valueMask);
                  if(!enabled) {
                    return false;
                  }
                }
                return enabled;
              }
            
              public boolean isEventMaskEnabled(
                  @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum maskEnum,
                  @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum... maskEnums) {
                boolean enabled = maskEnum.isEnabled(eventMask);
                if(!enabled) {
                  return false;
                }
                for(com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum m : maskEnums) {
                  java.util.Objects.requireNonNull(m, "maskEnums must not contain null");
                  enabled &= m.isEnabled(eventMask);
                  if(!enabled) {
                    return false;
                  }
                }
                return enabled;
              }
            
              public boolean isDoNotPropogateMaskEnabled(
                  @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum maskEnum,
                  @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum... maskEnums) {
                boolean enabled = maskEnum.isEnabled(doNotPropogateMask);
                if(!enabled) {
                  return false;
                }
                for(com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum m : maskEnums) {
                  java.util.Objects.requireNonNull(m, "maskEnums must not contain null");
                  enabled &= m.isEnabled(doNotPropogateMask);
                  if(!enabled) {
                    return false;
                  }
                }
                return enabled;
              }
            
              @java.lang.Override
              public int getSize() {
                return 32 + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACK_PIXMAP) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACK_PIXEL) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BORDER_PIXMAP) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BORDER_PIXEL) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BIT_GRAVITY) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.WIN_GRAVITY) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACKING_STORE) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACKING_PLANES) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACKING_PIXEL) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.OVERRIDE_REDIRECT) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.SAVE_UNDER) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.EVENT_MASK) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.DONT_PROPAGATE) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.COLORMAP) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.CURSOR) ? 4 : 0);
              }
            
              public static class CreateWindowRequestBuilder {
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder clazz(
                    com.github.moaxcp.x11client.protocol.xproto.WindowClassEnum clazz) {
                  this.clazz = (short) clazz.getValue();
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder clazz(
                    short clazz) {
                  this.clazz = clazz;
                  return this;
                }
            
                private com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder valueMask(
                    int valueMask) {
                  this.valueMask = valueMask;
                  return this;
                }
            
                public boolean isValueMaskEnabled(
                    @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.CwEnum maskEnum,
                    @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.CwEnum... maskEnums) {
                  boolean enabled = maskEnum.isEnabled(valueMask);
                  if(!enabled) {
                    return false;
                  }
                  for(com.github.moaxcp.x11client.protocol.xproto.CwEnum m : maskEnums) {
                    java.util.Objects.requireNonNull(m, "maskEnums must not contain null");
                    enabled &= m.isEnabled(valueMask);
                    if(!enabled) {
                      return false;
                    }
                  }
                  return enabled;
                }
            
                private com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder valueMaskEnable(
                    com.github.moaxcp.x11client.protocol.xproto.CwEnum maskEnum,
                    com.github.moaxcp.x11client.protocol.xproto.CwEnum... maskEnums) {
                  valueMask((int) maskEnum.enableFor(valueMask));
                  for(com.github.moaxcp.x11client.protocol.xproto.CwEnum m : maskEnums) {
                    java.util.Objects.requireNonNull(m, "maskEnums must not contain null");
                    valueMask((int) m.enableFor(valueMask));
                  }
                  return this;
                }
            
                private com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder valueMaskDisable(
                    com.github.moaxcp.x11client.protocol.xproto.CwEnum maskEnum,
                    com.github.moaxcp.x11client.protocol.xproto.CwEnum... maskEnums) {
                  valueMask((int) maskEnum.disableFor(valueMask));
                  for(com.github.moaxcp.x11client.protocol.xproto.CwEnum m : maskEnums) {
                    java.util.Objects.requireNonNull(m, "maskEnums must not contain null");
                    valueMask((int) m.disableFor(valueMask));
                  }
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder backgroundPixmap(
                    int backgroundPixmap) {
                  this.backgroundPixmap = backgroundPixmap;
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACK_PIXMAP);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder backgroundPixel(
                    int backgroundPixel) {
                  this.backgroundPixel = backgroundPixel;
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACK_PIXEL);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder borderPixmap(
                    int borderPixmap) {
                  this.borderPixmap = borderPixmap;
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BORDER_PIXMAP);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder borderPixel(
                    int borderPixel) {
                  this.borderPixel = borderPixel;
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BORDER_PIXEL);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder bitGravity(
                    int bitGravity) {
                  this.bitGravity = bitGravity;
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BIT_GRAVITY);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder bitGravity(
                    com.github.moaxcp.x11client.protocol.xproto.GravityEnum bitGravity) {
                  this.bitGravity = (int) bitGravity.getValue();
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BIT_GRAVITY);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder winGravity(
                    int winGravity) {
                  this.winGravity = winGravity;
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.WIN_GRAVITY);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder winGravity(
                    com.github.moaxcp.x11client.protocol.xproto.GravityEnum winGravity) {
                  this.winGravity = (int) winGravity.getValue();
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.WIN_GRAVITY);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder backingStore(
                    int backingStore) {
                  this.backingStore = backingStore;
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACKING_STORE);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder backingStore(
                    com.github.moaxcp.x11client.protocol.xproto.BackingStoreEnum backingStore) {
                  this.backingStore = (int) backingStore.getValue();
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACKING_STORE);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder backingPlanes(
                    int backingPlanes) {
                  this.backingPlanes = backingPlanes;
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACKING_PLANES);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder backingPixel(
                    int backingPixel) {
                  this.backingPixel = backingPixel;
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACKING_PIXEL);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder overrideRedirect(
                    int overrideRedirect) {
                  this.overrideRedirect = overrideRedirect;
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.OVERRIDE_REDIRECT);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder saveUnder(
                    int saveUnder) {
                  this.saveUnder = saveUnder;
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.SAVE_UNDER);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder eventMask(
                    int eventMask) {
                  this.eventMask = eventMask;
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.EVENT_MASK);
                  return this;
                }
            
                public boolean isEventMaskEnabled(
                    @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum maskEnum,
                    @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum... maskEnums) {
                  boolean enabled = maskEnum.isEnabled(eventMask);
                  if(!enabled) {
                    return false;
                  }
                  for(com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum m : maskEnums) {
                    java.util.Objects.requireNonNull(m, "maskEnums must not contain null");
                    enabled &= m.isEnabled(eventMask);
                    if(!enabled) {
                      return false;
                    }
                  }
                  return enabled;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder eventMaskEnable(
                    com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum maskEnum,
                    com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum... maskEnums) {
                  eventMask((int) maskEnum.enableFor(eventMask));
                  for(com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum m : maskEnums) {
                    java.util.Objects.requireNonNull(m, "maskEnums must not contain null");
                    eventMask((int) m.enableFor(eventMask));
                  }
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder eventMaskDisable(
                    com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum maskEnum,
                    com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum... maskEnums) {
                  eventMask((int) maskEnum.disableFor(eventMask));
                  for(com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum m : maskEnums) {
                    java.util.Objects.requireNonNull(m, "maskEnums must not contain null");
                    eventMask((int) m.disableFor(eventMask));
                  }
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder doNotPropogateMask(
                    int doNotPropogateMask) {
                  this.doNotPropogateMask = doNotPropogateMask;
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.DONT_PROPAGATE);
                  return this;
                }
            
                public boolean isDoNotPropogateMaskEnabled(
                    @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum maskEnum,
                    @lombok.NonNull com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum... maskEnums) {
                  boolean enabled = maskEnum.isEnabled(doNotPropogateMask);
                  if(!enabled) {
                    return false;
                  }
                  for(com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum m : maskEnums) {
                    java.util.Objects.requireNonNull(m, "maskEnums must not contain null");
                    enabled &= m.isEnabled(doNotPropogateMask);
                    if(!enabled) {
                      return false;
                    }
                  }
                  return enabled;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder doNotPropogateMaskEnable(
                    com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum maskEnum,
                    com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum... maskEnums) {
                  doNotPropogateMask((int) maskEnum.enableFor(doNotPropogateMask));
                  for(com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum m : maskEnums) {
                    java.util.Objects.requireNonNull(m, "maskEnums must not contain null");
                    doNotPropogateMask((int) m.enableFor(doNotPropogateMask));
                  }
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder doNotPropogateMaskDisable(
                    com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum maskEnum,
                    com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum... maskEnums) {
                  doNotPropogateMask((int) maskEnum.disableFor(doNotPropogateMask));
                  for(com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum m : maskEnums) {
                    java.util.Objects.requireNonNull(m, "maskEnums must not contain null");
                    doNotPropogateMask((int) m.disableFor(doNotPropogateMask));
                  }
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder colormap(
                    int colormap) {
                  this.colormap = colormap;
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.COLORMAP);
                  return this;
                }
            
                public com.github.moaxcp.x11client.protocol.xproto.CreateWindowRequest.CreateWindowRequestBuilder cursor(
                    int cursor) {
                  this.cursor = cursor;
                  valueMaskEnable(com.github.moaxcp.x11client.protocol.xproto.CwEnum.CURSOR);
                  return this;
                }
            
                public int getSize() {
                  return 32 + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACK_PIXMAP) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACK_PIXEL) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BORDER_PIXMAP) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BORDER_PIXEL) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BIT_GRAVITY) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.WIN_GRAVITY) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACKING_STORE) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACKING_PLANES) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.BACKING_PIXEL) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.OVERRIDE_REDIRECT) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.SAVE_UNDER) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.EVENT_MASK) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.DONT_PROPAGATE) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.COLORMAP) ? 4 : 0) + (isValueMaskEnabled(com.github.moaxcp.x11client.protocol.xproto.CwEnum.CURSOR) ? 4 : 0);
                }
              }
            }
        '''.stripIndent()
    }
}
