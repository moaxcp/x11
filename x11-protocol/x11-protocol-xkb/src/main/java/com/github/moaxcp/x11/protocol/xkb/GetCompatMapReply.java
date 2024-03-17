package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.Popcount;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetCompatMapReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";

  private byte deviceID;

  private short sequenceNumber;

  private short firstSIRtrn;

  private short nTotalSI;

  @NonNull
  private List<SymInterpret> siRtrn;

  @NonNull
  private List<ModDef> groupRtrn;

  public static GetCompatMapReply readGetCompatMapReply(byte deviceID, short sequenceNumber,
      X11Input in) throws IOException {
    GetCompatMapReply.GetCompatMapReplyBuilder javaBuilder = GetCompatMapReply.builder();
    int length = in.readCard32();
    byte groupsRtrn = in.readCard8();
    byte[] pad5 = in.readPad(1);
    short firstSIRtrn = in.readCard16();
    short nSIRtrn = in.readCard16();
    short nTotalSI = in.readCard16();
    byte[] pad9 = in.readPad(16);
    List<SymInterpret> siRtrn = new ArrayList<>(Short.toUnsignedInt(nSIRtrn));
    for(int i = 0; i < Short.toUnsignedInt(nSIRtrn); i++) {
      siRtrn.add(SymInterpret.readSymInterpret(in));
    }
    List<ModDef> groupRtrn = new ArrayList<>(Popcount.popcount(Byte.toUnsignedInt(groupsRtrn)));
    for(int i = 0; i < Popcount.popcount(Byte.toUnsignedInt(groupsRtrn)); i++) {
      groupRtrn.add(ModDef.readModDef(in));
    }
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.firstSIRtrn(firstSIRtrn);
    javaBuilder.nTotalSI(nTotalSI);
    javaBuilder.siRtrn(siRtrn);
    javaBuilder.groupRtrn(groupRtrn);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    byte groupsRtrn = (byte) groupRtrn.size();
    out.writeCard8(groupsRtrn);
    out.writePad(1);
    out.writeCard16(firstSIRtrn);
    short nSIRtrn = (short) siRtrn.size();
    out.writeCard16(nSIRtrn);
    out.writeCard16(nTotalSI);
    out.writePad(16);
    for(SymInterpret t : siRtrn) {
      t.write(out);
    }
    for(ModDef t : groupRtrn) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(siRtrn) + XObject.sizeOf(groupRtrn);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetCompatMapReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(siRtrn) + XObject.sizeOf(groupRtrn);
    }
  }
}
