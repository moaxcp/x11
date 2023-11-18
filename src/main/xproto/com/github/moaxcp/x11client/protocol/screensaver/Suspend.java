package com.github.moaxcp.x11client.protocol.screensaver;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Suspend implements OneWayRequest, ScreensaverObject {
  public static final byte OPCODE = 5;

  private int suspend;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Suspend readSuspend(X11Input in) throws IOException {
    Suspend.SuspendBuilder javaBuilder = Suspend.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int suspend = in.readCard32();
    javaBuilder.suspend(suspend);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(suspend);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class SuspendBuilder {
    public int getSize() {
      return 8;
    }
  }
}
