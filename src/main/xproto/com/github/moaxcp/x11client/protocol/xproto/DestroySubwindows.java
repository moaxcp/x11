package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DestroySubwindows implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 5;

  private int window;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DestroySubwindows readDestroySubwindows(X11Input in) throws IOException {
    DestroySubwindows.DestroySubwindowsBuilder javaBuilder = DestroySubwindows.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class DestroySubwindowsBuilder {
    public int getSize() {
      return 8;
    }
  }
}
