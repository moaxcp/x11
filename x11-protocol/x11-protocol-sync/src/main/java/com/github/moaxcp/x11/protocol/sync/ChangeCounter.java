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
public class ChangeCounter implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";

  public static final byte OPCODE = 4;

  private int counter;

  @NonNull
  private Int64 amount;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeCounter readChangeCounter(X11Input in) throws IOException {
    ChangeCounter.ChangeCounterBuilder javaBuilder = ChangeCounter.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int counter = in.readCard32();
    Int64 amount = Int64.readInt64(in);
    javaBuilder.counter(counter);
    javaBuilder.amount(amount);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(counter);
    amount.write(out);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ChangeCounterBuilder {
    public int getSize() {
      return 16;
    }
  }
}
