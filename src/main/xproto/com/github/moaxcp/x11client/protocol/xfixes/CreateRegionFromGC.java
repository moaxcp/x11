package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateRegionFromGC implements OneWayRequest, XfixesObject {
  public static final byte OPCODE = 8;

  private int region;

  private int gc;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateRegionFromGC readCreateRegionFromGC(X11Input in) throws IOException {
    CreateRegionFromGC.CreateRegionFromGCBuilder javaBuilder = CreateRegionFromGC.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int region = in.readCard32();
    int gc = in.readCard32();
    javaBuilder.region(region);
    javaBuilder.gc(gc);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(region);
    out.writeCard32(gc);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class CreateRegionFromGCBuilder {
    public int getSize() {
      return 12;
    }
  }
}
