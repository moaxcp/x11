package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Glyphinfo implements XStruct, RenderObject {
  private short width;

  private short height;

  private short x;

  private short y;

  private short xOff;

  private short yOff;

  public static Glyphinfo readGlyphinfo(X11Input in) throws IOException {
    Glyphinfo.GlyphinfoBuilder javaBuilder = Glyphinfo.builder();
    short width = in.readCard16();
    short height = in.readCard16();
    short x = in.readInt16();
    short y = in.readInt16();
    short xOff = in.readInt16();
    short yOff = in.readInt16();
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.xOff(xOff);
    javaBuilder.yOff(yOff);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeInt16(xOff);
    out.writeInt16(yOff);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GlyphinfoBuilder {
    public int getSize() {
      return 12;
    }
  }
}
