package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DestroyGLXPixmap implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 15;

  private int glxPixmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DestroyGLXPixmap readDestroyGLXPixmap(X11Input in) throws IOException {
    DestroyGLXPixmap.DestroyGLXPixmapBuilder javaBuilder = DestroyGLXPixmap.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int glxPixmap = in.readCard32();
    javaBuilder.glxPixmap(glxPixmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(glxPixmap);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DestroyGLXPixmapBuilder {
    public int getSize() {
      return 8;
    }
  }
}
