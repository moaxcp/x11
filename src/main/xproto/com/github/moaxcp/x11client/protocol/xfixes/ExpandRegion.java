package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExpandRegion implements OneWayRequest, XfixesObject {
  public static final byte OPCODE = 28;

  private int source;

  private int destination;

  private short left;

  private short right;

  private short top;

  private short bottom;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ExpandRegion readExpandRegion(X11Input in) throws IOException {
    ExpandRegion.ExpandRegionBuilder javaBuilder = ExpandRegion.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int source = in.readCard32();
    int destination = in.readCard32();
    short left = in.readCard16();
    short right = in.readCard16();
    short top = in.readCard16();
    short bottom = in.readCard16();
    javaBuilder.source(source);
    javaBuilder.destination(destination);
    javaBuilder.left(left);
    javaBuilder.right(right);
    javaBuilder.top(top);
    javaBuilder.bottom(bottom);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(source);
    out.writeCard32(destination);
    out.writeCard16(left);
    out.writeCard16(right);
    out.writeCard16(top);
    out.writeCard16(bottom);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public static class ExpandRegionBuilder {
    public int getSize() {
      return 20;
    }
  }
}
