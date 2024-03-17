package com.github.moaxcp.x11.protocol.damage;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Subtract implements OneWayRequest {
  public static final String PLUGIN_NAME = "damage";

  public static final byte OPCODE = 3;

  private int damage;

  private int repair;

  private int parts;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Subtract readSubtract(X11Input in) throws IOException {
    Subtract.SubtractBuilder javaBuilder = Subtract.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int damage = in.readCard32();
    int repair = in.readCard32();
    int parts = in.readCard32();
    javaBuilder.damage(damage);
    javaBuilder.repair(repair);
    javaBuilder.parts(parts);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(damage);
    out.writeCard32(repair);
    out.writeCard32(parts);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SubtractBuilder {
    public int getSize() {
      return 16;
    }
  }
}
