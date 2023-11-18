package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateGLXPixmap implements OneWayRequest, GlxObject {
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
    byte[] pad1 = in.readPad(1);
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
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
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

  public static class CreateGLXPixmapBuilder {
    public int getSize() {
      return 20;
    }
  }
}
