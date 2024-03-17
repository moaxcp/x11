package com.github.moaxcp.x11.protocol.xf86vidmode;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetGammaRamp implements OneWayRequest {
  public static final String PLUGIN_NAME = "xf86vidmode";

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
    byte majorOpcode = in.readCard8();
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
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetGammaRampBuilder {
    public int getSize() {
      return 8 + 2 * red.size() + 2 * green.size() + 2 * blue.size();
    }
  }
}
