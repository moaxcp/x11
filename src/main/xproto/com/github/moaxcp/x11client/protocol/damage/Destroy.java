package com.github.moaxcp.x11client.protocol.damage;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Destroy implements OneWayRequest, DamageObject {
  public static final byte OPCODE = 2;

  private int damage;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Destroy readDestroy(X11Input in) throws IOException {
    Destroy.DestroyBuilder javaBuilder = Destroy.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int damage = in.readCard32();
    javaBuilder.damage(damage);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(damage);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class DestroyBuilder {
    public int getSize() {
      return 8;
    }
  }
}
