package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CloseFont implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 46;

  private int font;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CloseFont readCloseFont(X11Input in) throws IOException {
    CloseFont.CloseFontBuilder javaBuilder = CloseFont.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int font = in.readCard32();
    javaBuilder.font(font);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(font);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class CloseFontBuilder {
    public int getSize() {
      return 8;
    }
  }
}
