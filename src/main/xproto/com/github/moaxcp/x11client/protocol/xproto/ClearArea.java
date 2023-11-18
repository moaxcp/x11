package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClearArea implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 61;

  private boolean exposures;

  private int window;

  private short x;

  private short y;

  private short width;

  private short height;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ClearArea readClearArea(X11Input in) throws IOException {
    ClearArea.ClearAreaBuilder javaBuilder = ClearArea.builder();
    boolean exposures = in.readBool();
    short length = in.readCard16();
    int window = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    javaBuilder.exposures(exposures);
    javaBuilder.window(window);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeBool(exposures);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class ClearAreaBuilder {
    public int getSize() {
      return 16;
    }
  }
}
