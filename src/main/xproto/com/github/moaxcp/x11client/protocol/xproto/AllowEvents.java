package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AllowEvents implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 35;

  private byte mode;

  private int time;

  public byte getOpCode() {
    return OPCODE;
  }

  public static AllowEvents readAllowEvents(X11Input in) throws IOException {
    AllowEvents.AllowEventsBuilder javaBuilder = AllowEvents.builder();
    byte mode = in.readCard8();
    short length = in.readCard16();
    int time = in.readCard32();
    javaBuilder.mode(mode);
    javaBuilder.time(time);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(mode);
    out.writeCard16((short) getLength());
    out.writeCard32(time);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class AllowEventsBuilder {
    public AllowEvents.AllowEventsBuilder mode(Allow mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public AllowEvents.AllowEventsBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
