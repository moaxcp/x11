package com.github.moaxcp.x11client.protocol.xf86vidmode;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetGammaRamp implements OneWayRequest, Xf86vidmodeObject {
  public static final byte OPCODE = 18;

  private short screen;

  @NonNull
  private List<Short> red;

  @NonNull
  private List<Short> green;

  @NonNull
  private List<Short> blue;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetGammaRamp readSetGammaRamp(X11Input in) throws IOException {
    SetGammaRamp.SetGammaRampBuilder javaBuilder = SetGammaRamp.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short screen = in.readCard16();
    short size = in.readCard16();
    List<Short> red = in.readCard16((Short.toUnsignedInt(size) + 1) & (~ (1)));
    List<Short> green = in.readCard16((Short.toUnsignedInt(size) + 1) & (~ (1)));
    List<Short> blue = in.readCard16((Short.toUnsignedInt(size) + 1) & (~ (1)));
    javaBuilder.screen(screen);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(screen);
    short size = (short) blue.size();
    out.writeCard16(size);
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 2 * red.size() + 2 * green.size() + 2 * blue.size();
  }

  public static class SetGammaRampBuilder {
    public int getSize() {
      return 8 + 2 * red.size() + 2 * green.size() + 2 * blue.size();
    }
  }
}
