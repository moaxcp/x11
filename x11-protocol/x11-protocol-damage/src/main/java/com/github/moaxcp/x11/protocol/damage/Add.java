package com.github.moaxcp.x11.protocol.damage;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Add implements OneWayRequest {
  public static final String PLUGIN_NAME = "damage";

  public static final byte OPCODE = 4;

  private int drawable;

  private int region;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Add readAdd(X11Input in) throws IOException {
    Add.AddBuilder javaBuilder = Add.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    int region = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.region(region);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(region);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AddBuilder {
    public int getSize() {
      return 12;
    }
  }
}
