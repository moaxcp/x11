package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateGLXPixmap implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 13;

  private int screen;

  private int visual;

  private int pixmap;

  private int glxPixmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateGLXPixmap readCreateGLXPixmap(X11Input in) throws IOException {
    CreateGLXPixmap.CreateGLXPixmapBuilder javaBuilder = CreateGLXPixmap.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int screen = in.readCard32();
    int visual = in.readCard32();
    int pixmap = in.readCard32();
    int glxPixmap = in.readCard32();
    javaBuilder.screen(screen);
    javaBuilder.visual(visual);
    javaBuilder.pixmap(pixmap);
    javaBuilder.glxPixmap(glxPixmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(screen);
    out.writeCard32(visual);
    out.writeCard32(pixmap);
    out.writeCard32(glxPixmap);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateGLXPixmapBuilder {
    public int getSize() {
      return 20;
    }
  }
}
