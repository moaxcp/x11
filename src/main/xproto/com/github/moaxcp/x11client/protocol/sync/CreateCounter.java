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
public class CreateCounter implements OneWayRequest, SyncObject {
  public static final byte OPCODE = 2;

  private int id;

  @NonNull
  private Int64 initialValue;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateCounter readCreateCounter(X11Input in) throws IOException {
    CreateCounter.CreateCounterBuilder javaBuilder = CreateCounter.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int id = in.readCard32();
    Int64 initialValue = Int64.readInt64(in);
    javaBuilder.id(id);
    javaBuilder.initialValue(initialValue);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(id);
    initialValue.write(out);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class CreateCounterBuilder {
    public int getSize() {
      return 16;
    }
  }
}
