package com.github.moaxcp.x11client.protocol.damage;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Add implements OneWayRequest, DamageObject {
  public static final byte OPCODE = 4;

  private int drawable;

  private int region;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Add readAdd(X11Input in) throws IOException {
    Add.AddBuilder javaBuilder = Add.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    int region = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.region(region);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(region);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class AddBuilder {
    public int getSize() {
      return 12;
    }
  }
}
