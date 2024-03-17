package com.github.moaxcp.x11.protocol.xf86vidmode;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetViewPort implements OneWayRequest {
  public static final String PLUGIN_NAME = "xf86vidmode";

  public static final byte OPCODE = 12;

  private short screen;

  private int x;

  private int y;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetViewPort readSetViewPort(X11Input in) throws IOException {
    SetViewPort.SetViewPortBuilder javaBuilder = SetViewPort.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short screen = in.readCard16();
    byte[] pad4 = in.readPad(2);
    int x = in.readCard32();
    int y = in.readCard32();
    javaBuilder.screen(screen);
    javaBuilder.x(x);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(screen);
    out.writePad(2);
    out.writeCard32(x);
    out.writeCard32(y);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetViewPortBuilder {
    public int getSize() {
      return 16;
    }
  }
}
