package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetCounter implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";

  public static final byte OPCODE = 3;

  private int counter;

  @NonNull
  private Int64 value;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetCounter readSetCounter(X11Input in) throws IOException {
    SetCounter.SetCounterBuilder javaBuilder = SetCounter.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int counter = in.readCard32();
    Int64 value = Int64.readInt64(in);
    javaBuilder.counter(counter);
    javaBuilder.value(value);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(counter);
    value.write(out);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetCounterBuilder {
    public int getSize() {
      return 16;
    }
  }
}
