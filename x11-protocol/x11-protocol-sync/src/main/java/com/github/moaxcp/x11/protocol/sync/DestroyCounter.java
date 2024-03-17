package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DestroyCounter implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";

  public static final byte OPCODE = 6;

  private int counter;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DestroyCounter readDestroyCounter(X11Input in) throws IOException {
    DestroyCounter.DestroyCounterBuilder javaBuilder = DestroyCounter.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int counter = in.readCard32();
    javaBuilder.counter(counter);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(counter);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DestroyCounterBuilder {
    public int getSize() {
      return 8;
    }
  }
}
