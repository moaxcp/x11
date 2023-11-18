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
public class SetCounter implements OneWayRequest, SyncObject {
  public static final byte OPCODE = 3;

  private int counter;

  @NonNull
  private Int64 value;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetCounter readSetCounter(X11Input in) throws IOException {
    SetCounter.SetCounterBuilder javaBuilder = SetCounter.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int counter = in.readCard32();
    Int64 value = Int64.readInt64(in);
    javaBuilder.counter(counter);
    javaBuilder.value(value);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(counter);
    value.write(out);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class SetCounterBuilder {
    public int getSize() {
      return 16;
    }
  }
}
