package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReparentWindow implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 7;

  private int window;

  private int parent;

  private short x;

  private short y;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ReparentWindow readReparentWindow(X11Input in) throws IOException {
    ReparentWindow.ReparentWindowBuilder javaBuilder = ReparentWindow.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    int parent = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    javaBuilder.window(window);
    javaBuilder.parent(parent);
    javaBuilder.x(x);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(parent);
    out.writeInt16(x);
    out.writeInt16(y);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class ReparentWindowBuilder {
    public int getSize() {
      return 16;
    }
  }
}
