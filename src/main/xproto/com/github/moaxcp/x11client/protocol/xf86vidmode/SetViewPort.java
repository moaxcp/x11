package com.github.moaxcp.x11client.protocol.xf86vidmode;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetViewPort implements OneWayRequest, Xf86vidmodeObject {
  public static final byte OPCODE = 12;

  private short screen;

  private int x;

  private int y;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetViewPort readSetViewPort(X11Input in) throws IOException {
    SetViewPort.SetViewPortBuilder javaBuilder = SetViewPort.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short screen = in.readCard16();
    byte[] pad4 = in.readPad(2);
    int x = in.readCard32();
    int y = in.readCard32();
    javaBuilder.screen(screen);
    javaBuilder.x(x);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(screen);
    out.writePad(2);
    out.writeCard32(x);
    out.writeCard32(y);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class SetViewPortBuilder {
    public int getSize() {
      return 16;
    }
  }
}
