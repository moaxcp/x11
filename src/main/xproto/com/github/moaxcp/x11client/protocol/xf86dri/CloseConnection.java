package com.github.moaxcp.x11client.protocol.xf86dri;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CloseConnection implements OneWayRequest, Xf86driObject {
  public static final byte OPCODE = 3;

  private int screen;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CloseConnection readCloseConnection(X11Input in) throws IOException {
    CloseConnection.CloseConnectionBuilder javaBuilder = CloseConnection.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int screen = in.readCard32();
    javaBuilder.screen(screen);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(screen);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class CloseConnectionBuilder {
    public int getSize() {
      return 8;
    }
  }
}
