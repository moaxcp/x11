package com.github.moaxcp.x11.protocol.xf86vidmode;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetGamma implements OneWayRequest {
  public static final String PLUGIN_NAME = "xf86vidmode";

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
    byte majorOpcode = in.readCard8();
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
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetGammaBuilder {
    public int getSize() {
      return 32;
    }
  }
}
