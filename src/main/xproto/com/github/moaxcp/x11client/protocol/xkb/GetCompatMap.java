package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetCompatMap implements TwoWayRequest<GetCompatMapReply>, XkbObject {
  public static final byte OPCODE = 10;

  private short deviceSpec;

  private byte groups;

  private boolean getAllSI;

  private short firstSI;

  private short nSI;

  public XReplyFunction<GetCompatMapReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetCompatMapReply.readGetCompatMapReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetCompatMap readGetCompatMap(X11Input in) throws IOException {
    GetCompatMap.GetCompatMapBuilder javaBuilder = GetCompatMap.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte groups = in.readCard8();
    boolean getAllSI = in.readBool();
    short firstSI = in.readCard16();
    short nSI = in.readCard16();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.groups(groups);
    javaBuilder.getAllSI(getAllSI);
    javaBuilder.firstSI(firstSI);
    javaBuilder.nSI(nSI);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard8(groups);
    out.writeBool(getAllSI);
    out.writeCard16(firstSI);
    out.writeCard16(nSI);
  }

  public boolean isGroupsEnabled(@NonNull SetOfGroup... maskEnums) {
    for(SetOfGroup m : maskEnums) {
      if(!m.isEnabled(groups)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetCompatMapBuilder {
    public boolean isGroupsEnabled(@NonNull SetOfGroup... maskEnums) {
      for(SetOfGroup m : maskEnums) {
        if(!m.isEnabled(groups)) {
          return false;
        }
      }
      return true;
    }

    public GetCompatMap.GetCompatMapBuilder groupsEnable(SetOfGroup... maskEnums) {
      for(SetOfGroup m : maskEnums) {
        groups((byte) m.enableFor(groups));
      }
      return this;
    }

    public GetCompatMap.GetCompatMapBuilder groupsDisable(SetOfGroup... maskEnums) {
      for(SetOfGroup m : maskEnums) {
        groups((byte) m.disableFor(groups));
      }
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
