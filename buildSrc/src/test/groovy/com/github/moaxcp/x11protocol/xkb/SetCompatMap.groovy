package com.github.moaxcp.x11protocol.xkb

import com.github.moaxcp.x11protocol.XmlSpec
import com.github.moaxcp.x11protocol.xcbparser.JavaRequest
import com.github.moaxcp.x11protocol.xcbparser.XTypeRequest

class SetCompatMap extends XmlSpec {
  def setCompatMap() {
    given:
    xmlBuilder.xcb() {
      typedef(oldname: 'CARD16', newname: 'DeviceSpec')

      'enum'(name: 'SetOfGroup') {
      }

      struct(name:'SymInterpret') {
      }

      struct(name: 'ModDef') {
      }

      request(name: 'SetCompatMap', opcode: "11") {
        field(name: 'deviceSpec', type: 'DeviceSpec')
        pad(bytes: '1')
        field(name: 'recomputeActions', type: 'BOOL')
        field(name: 'truncateSi', type: 'BOOL')
        field(name: 'groups', type: 'CARD8', mask: 'SetOfGroup')
        field(name: 'firstSI', type: 'CARD16')
        field(name: 'nSI', type: 'CARD16')
        pad(bytes: '2')
        list(name: 'si', type: 'SymInterpret') {
          fieldref('nSI')
        }
        list(name: 'groupMaps', type: 'ModDef') {
          popcount() {
            fieldref('groups')
          }
        }
      }
    }

    when:
    addChildNodes()
    XTypeRequest request = result.resolveXType('SetCompatMap')
    JavaRequest javaRequest = request.javaType

    then:
    javaRequest.typeSpec.toString() == '''\
        @lombok.Value
        @lombok.Builder
        public class SetCompatMap implements com.github.moaxcp.x11client.protocol.OneWayRequest, com.github.moaxcp.x11client.protocol.xproto.XprotoObject {
          public static final byte OPCODE = 11;
        
          private short deviceSpec;
        
          private boolean recomputeActions;
        
          private boolean truncateSi;
        
          private short firstSI;
        
          @lombok.NonNull
          private java.util.List<com.github.moaxcp.x11client.protocol.xproto.SymInterpret> si;
        
          @lombok.NonNull
          private java.util.List<com.github.moaxcp.x11client.protocol.xproto.ModDef> groupMaps;
        
          public byte getOpCode() {
            return OPCODE;
          }
        
          public static com.github.moaxcp.x11client.protocol.xproto.SetCompatMap readSetCompatMap(
              com.github.moaxcp.x11client.protocol.X11Input in) throws java.io.IOException {
            com.github.moaxcp.x11client.protocol.xproto.SetCompatMap.SetCompatMapBuilder javaBuilder = com.github.moaxcp.x11client.protocol.xproto.SetCompatMap.builder();
            in.readPad(1);
            short length = in.readCard16();
            short deviceSpec = in.readCard16();
            in.readPad(1);
            boolean recomputeActions = in.readBool();
            boolean truncateSi = in.readBool();
            byte groups = in.readCard8();
            short firstSI = in.readCard16();
            short nSI = in.readCard16();
            in.readPad(2);
            java.util.List<com.github.moaxcp.x11client.protocol.xproto.SymInterpret> si = new java.util.ArrayList<>(Short.toUnsignedInt(nSI));
            for(int i = 0; i < Short.toUnsignedInt(nSI); i++) {
              si.add(com.github.moaxcp.x11client.protocol.xproto.SymInterpret.readSymInterpret(in));
            }
            java.util.List<com.github.moaxcp.x11client.protocol.xproto.ModDef> groupMaps = new java.util.ArrayList<>(com.github.moaxcp.x11client.protocol.Popcount.popcount(Byte.toUnsignedInt(groups)));
            for(int i = 0; i < com.github.moaxcp.x11client.protocol.Popcount.popcount(Byte.toUnsignedInt(groups)); i++) {
              groupMaps.add(com.github.moaxcp.x11client.protocol.xproto.ModDef.readModDef(in));
            }
            javaBuilder.deviceSpec(deviceSpec);
            javaBuilder.recomputeActions(recomputeActions);
            javaBuilder.truncateSi(truncateSi);
            javaBuilder.firstSI(firstSI);
            javaBuilder.si(si);
            javaBuilder.groupMaps(groupMaps);
            in.readPadAlign(javaBuilder.getSize());
            return javaBuilder.build();
          }
        
          @java.lang.Override
          public void write(byte offset, com.github.moaxcp.x11client.protocol.X11Output out) throws
              java.io.IOException {
            out.writeCard8((byte)(java.lang.Byte.toUnsignedInt(OPCODE) + java.lang.Byte.toUnsignedInt(offset)));
            out.writePad(1);
            out.writeCard16((short) getLength());
            out.writeCard16(deviceSpec);
            out.writePad(1);
            out.writeBool(recomputeActions);
            out.writeBool(truncateSi);
            byte groups = (byte) groupMaps.size();
            out.writeCard8(groups);
            out.writeCard16(firstSI);
            short nSI = (short) si.size();
            out.writeCard16(nSI);
            out.writePad(2);
            for(com.github.moaxcp.x11client.protocol.xproto.SymInterpret t : si) {
              t.write(out);
            }
            for(com.github.moaxcp.x11client.protocol.xproto.ModDef t : groupMaps) {
              t.write(out);
            }
            out.writePadAlign(getSize());
          }
        
          @java.lang.Override
          public int getSize() {
            return 16 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(si) + com.github.moaxcp.x11client.protocol.XObject.sizeOf(groupMaps);
          }
        
          public static class SetCompatMapBuilder {
            public int getSize() {
              return 16 + com.github.moaxcp.x11client.protocol.XObject.sizeOf(si) + com.github.moaxcp.x11client.protocol.XObject.sizeOf(groupMaps);
            }
          }
        }
    '''.stripIndent()
  }
}
