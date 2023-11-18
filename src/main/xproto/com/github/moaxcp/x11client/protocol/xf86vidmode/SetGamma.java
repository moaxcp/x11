package com.github.moaxcp.x11client.protocol.xf86vidmode;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetGamma implements OneWayRequest, Xf86vidmodeObject {
  public static final byte OPCODE = 15;

  private short screen;

  private int red;

  private int green;

  private int blue;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetGamma readSetGamma(X11Input in) throws IOException {
    SetGamma.SetGammaBuilder javaBuilder = SetGamma.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short screen = in.readCard16();
    byte[] pad4 = in.readPad(2);
    int red = in.readCard32();
    int green = in.readCard32();
    int blue = in.readCard32();
    byte[] pad8 = in.readPad(12);
    javaBuilder.screen(screen);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(screen);
    out.writePad(2);
    out.writeCard32(red);
    out.writeCard32(green);
    out.writeCard32(blue);
    out.writePad(12);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class SetGammaBuilder {
    public int getSize() {
      return 32;
    }
  }
}
