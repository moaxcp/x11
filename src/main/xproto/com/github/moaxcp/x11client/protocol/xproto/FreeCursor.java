package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FreeCursor implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 95;

  private int cursor;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreeCursor readFreeCursor(X11Input in) throws IOException {
    FreeCursor.FreeCursorBuilder javaBuilder = FreeCursor.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cursor = in.readCard32();
    javaBuilder.cursor(cursor);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cursor);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class FreeCursorBuilder {
    public int getSize() {
      return 8;
    }
  }
}
