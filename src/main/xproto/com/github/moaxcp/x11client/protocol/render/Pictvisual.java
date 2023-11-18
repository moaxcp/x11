package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Pictvisual implements XStruct, RenderObject {
  private int visual;

  private int format;

  public static Pictvisual readPictvisual(X11Input in) throws IOException {
    Pictvisual.PictvisualBuilder javaBuilder = Pictvisual.builder();
    int visual = in.readCard32();
    int format = in.readCard32();
    javaBuilder.visual(visual);
    javaBuilder.format(format);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(visual);
    out.writeCard32(format);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class PictvisualBuilder {
    public int getSize() {
      return 8;
    }
  }
}
