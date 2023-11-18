package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ChangeCounter implements OneWayRequest, SyncObject {
  public static final byte OPCODE = 4;

  private int counter;

  @NonNull
  private Int64 amount;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeCounter readChangeCounter(X11Input in) throws IOException {
    ChangeCounter.ChangeCounterBuilder javaBuilder = ChangeCounter.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int counter = in.readCard32();
    Int64 amount = Int64.readInt64(in);
    javaBuilder.counter(counter);
    javaBuilder.amount(amount);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(counter);
    amount.write(out);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class ChangeCounterBuilder {
    public int getSize() {
      return 16;
    }
  }
}
