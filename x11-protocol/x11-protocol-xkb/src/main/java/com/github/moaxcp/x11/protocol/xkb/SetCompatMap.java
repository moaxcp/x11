package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.Popcount;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetCompatMap implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte OPCODE = 11;

  private short deviceSpec;

  private boolean recomputeActions;

  private boolean truncateSI;

  private short firstSI;

  @NonNull
  private List<SymInterpret> si;

  @NonNull
  private List<ModDef> groupMaps;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetCompatMap readSetCompatMap(X11Input in) throws IOException {
    SetCompatMap.SetCompatMapBuilder javaBuilder = SetCompatMap.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte[] pad4 = in.readPad(1);
    boolean recomputeActions = in.readBool();
    boolean truncateSI = in.readBool();
    byte groups = in.readCard8();
    short firstSI = in.readCard16();
    short nSI = in.readCard16();
    byte[] pad10 = in.readPad(2);
    List<SymInterpret> si = new ArrayList<>(Short.toUnsignedInt(nSI));
    for(int i = 0; i < Short.toUnsignedInt(nSI); i++) {
      si.add(SymInterpret.readSymInterpret(in));
    }
    List<ModDef> groupMaps = new ArrayList<>(Popcount.popcount(Byte.toUnsignedInt(groups)));
    for(int i = 0; i < Popcount.popcount(Byte.toUnsignedInt(groups)); i++) {
      groupMaps.add(ModDef.readModDef(in));
    }
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.recomputeActions(recomputeActions);
    javaBuilder.truncateSI(truncateSI);
    javaBuilder.firstSI(firstSI);
    javaBuilder.si(si);
    javaBuilder.groupMaps(groupMaps);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writePad(1);
    out.writeBool(recomputeActions);
    out.writeBool(truncateSI);
    byte groups = (byte) groupMaps.size();
    out.writeCard8(groups);
    out.writeCard16(firstSI);
    short nSI = (short) si.size();
    out.writeCard16(nSI);
    out.writePad(2);
    for(SymInterpret t : si) {
      t.write(out);
    }
    for(ModDef t : groupMaps) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + XObject.sizeOf(si) + XObject.sizeOf(groupMaps);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetCompatMapBuilder {
    public int getSize() {
      return 16 + XObject.sizeOf(si) + XObject.sizeOf(groupMaps);
    }
  }
}
