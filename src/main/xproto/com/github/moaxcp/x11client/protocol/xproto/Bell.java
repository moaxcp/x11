package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Bell implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 104;

  private byte percent;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Bell readBell(X11Input in) throws IOException {
    Bell.BellBuilder javaBuilder = Bell.builder();
    byte percent = in.readInt8();
    short length = in.readCard16();
    javaBuilder.percent(percent);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeInt8(percent);
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public static class BellBuilder {
    public int getSize() {
      return 4;
    }
  }
}
