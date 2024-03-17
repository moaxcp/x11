package com.github.moaxcp.x11.protocol.screensaver;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Suspend implements OneWayRequest {
  public static final String PLUGIN_NAME = "screensaver";

  public static final byte OPCODE = 5;

  private int suspend;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Suspend readSuspend(X11Input in) throws IOException {
    Suspend.SuspendBuilder javaBuilder = Suspend.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int suspend = in.readCard32();
    javaBuilder.suspend(suspend);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(suspend);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SuspendBuilder {
    public int getSize() {
      return 8;
    }
  }
}
