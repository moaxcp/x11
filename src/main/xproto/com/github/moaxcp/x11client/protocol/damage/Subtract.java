package com.github.moaxcp.x11client.protocol.damage;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Subtract implements OneWayRequest, DamageObject {
  public static final byte OPCODE = 3;

  private int damage;

  private int repair;

  private int parts;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Subtract readSubtract(X11Input in) throws IOException {
    Subtract.SubtractBuilder javaBuilder = Subtract.builder();
    byte[] pad1 = in.readPad(1);
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
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(damage);
    out.writeCard32(repair);
    out.writeCard32(parts);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class SubtractBuilder {
    public int getSize() {
      return 16;
    }
  }
}
