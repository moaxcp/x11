package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeleteWindow implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 32;

  private int glxwindow;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeleteWindow readDeleteWindow(X11Input in) throws IOException {
    DeleteWindow.DeleteWindowBuilder javaBuilder = DeleteWindow.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int glxwindow = in.readCard32();
    javaBuilder.glxwindow(glxwindow);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(glxwindow);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeleteWindowBuilder {
    public int getSize() {
      return 8;
    }
  }
}
