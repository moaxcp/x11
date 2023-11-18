package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ScreenSize implements XStruct, RandrObject {
  private short width;

  private short height;

  private short mwidth;

  private short mheight;

  public static ScreenSize readScreenSize(X11Input in) throws IOException {
    ScreenSize.ScreenSizeBuilder javaBuilder = ScreenSize.builder();
    short width = in.readCard16();
    short height = in.readCard16();
    short mwidth = in.readCard16();
    short mheight = in.readCard16();
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.mwidth(mwidth);
    javaBuilder.mheight(mheight);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(mwidth);
    out.writeCard16(mheight);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class ScreenSizeBuilder {
    public int getSize() {
      return 8;
    }
  }
}
