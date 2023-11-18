package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetGCClipRegion implements OneWayRequest, XfixesObject {
  public static final byte OPCODE = 20;

  private int gc;

  private int region;

  private short xOrigin;

  private short yOrigin;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetGCClipRegion readSetGCClipRegion(X11Input in) throws IOException {
    SetGCClipRegion.SetGCClipRegionBuilder javaBuilder = SetGCClipRegion.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int gc = in.readCard32();
    int region = in.readCard32();
    short xOrigin = in.readInt16();
    short yOrigin = in.readInt16();
    javaBuilder.gc(gc);
    javaBuilder.region(region);
    javaBuilder.xOrigin(xOrigin);
    javaBuilder.yOrigin(yOrigin);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(gc);
    out.writeCard32(region);
    out.writeInt16(xOrigin);
    out.writeInt16(yOrigin);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class SetGCClipRegionBuilder {
    public int getSize() {
      return 16;
    }
  }
}
