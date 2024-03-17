package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Pictvisual implements XStruct {
  public static final String PLUGIN_NAME = "render";

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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PictvisualBuilder {
    public int getSize() {
      return 8;
    }
  }
}
