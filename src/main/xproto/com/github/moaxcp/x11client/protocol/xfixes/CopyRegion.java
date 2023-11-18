package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CopyRegion implements OneWayRequest, XfixesObject {
  public static final byte OPCODE = 12;

  private int source;

  private int destination;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CopyRegion readCopyRegion(X11Input in) throws IOException {
    CopyRegion.CopyRegionBuilder javaBuilder = CopyRegion.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int source = in.readCard32();
    int destination = in.readCard32();
    javaBuilder.source(source);
    javaBuilder.destination(destination);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(source);
    out.writeCard32(destination);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class CopyRegionBuilder {
    public int getSize() {
      return 12;
    }
  }
}
