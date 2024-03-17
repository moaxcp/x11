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
public class CreateCounter implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";

  public static final byte OPCODE = 2;

  private int id;

  @NonNull
  private Int64 initialValue;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateCounter readCreateCounter(X11Input in) throws IOException {
    CreateCounter.CreateCounterBuilder javaBuilder = CreateCounter.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int id = in.readCard32();
    Int64 initialValue = Int64.readInt64(in);
    javaBuilder.id(id);
    javaBuilder.initialValue(initialValue);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(id);
    initialValue.write(out);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateCounterBuilder {
    public int getSize() {
      return 16;
    }
  }
}
