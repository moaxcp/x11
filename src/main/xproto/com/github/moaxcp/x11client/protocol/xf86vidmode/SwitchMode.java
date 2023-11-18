package com.github.moaxcp.x11client.protocol.xf86vidmode;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SwitchMode implements OneWayRequest, Xf86vidmodeObject {
  public static final byte OPCODE = 3;

  private short screen;

  private short zoom;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SwitchMode readSwitchMode(X11Input in) throws IOException {
    SwitchMode.SwitchModeBuilder javaBuilder = SwitchMode.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short screen = in.readCard16();
    short zoom = in.readCard16();
    javaBuilder.screen(screen);
    javaBuilder.zoom(zoom);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(screen);
    out.writeCard16(zoom);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class SwitchModeBuilder {
    public int getSize() {
      return 8;
    }
  }
}
