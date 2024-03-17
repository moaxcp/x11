package com.github.moaxcp.x11.protocol.damage;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Destroy implements OneWayRequest {
  public static final String PLUGIN_NAME = "damage";

  public static final byte OPCODE = 2;

  private int damage;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Destroy readDestroy(X11Input in) throws IOException {
    Destroy.DestroyBuilder javaBuilder = Destroy.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int damage = in.readCard32();
    javaBuilder.damage(damage);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(damage);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DestroyBuilder {
    public int getSize() {
      return 8;
    }
  }
}
