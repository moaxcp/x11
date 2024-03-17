package com.github.moaxcp.x11.protocol.dri2;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SwapInterval implements OneWayRequest {
  public static final String PLUGIN_NAME = "dri2";

  public static final byte OPCODE = 12;

  private int drawable;

  private int interval;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SwapInterval readSwapInterval(X11Input in) throws IOException {
    SwapInterval.SwapIntervalBuilder javaBuilder = SwapInterval.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    int interval = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.interval(interval);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(interval);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SwapIntervalBuilder {
    public int getSize() {
      return 12;
    }
  }
}
