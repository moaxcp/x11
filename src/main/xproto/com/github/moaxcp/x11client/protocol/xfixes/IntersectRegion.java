package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class IntersectRegion implements OneWayRequest, XfixesObject {
  public static final byte OPCODE = 14;

  private int source1;

  private int source2;

  private int destination;

  public byte getOpCode() {
    return OPCODE;
  }

  public static IntersectRegion readIntersectRegion(X11Input in) throws IOException {
    IntersectRegion.IntersectRegionBuilder javaBuilder = IntersectRegion.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int source1 = in.readCard32();
    int source2 = in.readCard32();
    int destination = in.readCard32();
    javaBuilder.source1(source1);
    javaBuilder.source2(source2);
    javaBuilder.destination(destination);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(source1);
    out.writeCard32(source2);
    out.writeCard32(destination);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class IntersectRegionBuilder {
    public int getSize() {
      return 16;
    }
  }
}
