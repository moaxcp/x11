package com.github.moaxcp.x11client.protocol.dri2;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SwapInterval implements OneWayRequest, Dri2Object {
  public static final byte OPCODE = 12;

  private int drawable;

  private int interval;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SwapInterval readSwapInterval(X11Input in) throws IOException {
    SwapInterval.SwapIntervalBuilder javaBuilder = SwapInterval.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    int interval = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.interval(interval);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(interval);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class SwapIntervalBuilder {
    public int getSize() {
      return 12;
    }
  }
}
