package com.github.moaxcp.x11.protocol.xinerama;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ScreenInfo implements XStruct {
  public static final String PLUGIN_NAME = "xinerama";

  private short xOrg;

  private short yOrg;

  private short width;

  private short height;

  public static ScreenInfo readScreenInfo(X11Input in) throws IOException {
    ScreenInfo.ScreenInfoBuilder javaBuilder = ScreenInfo.builder();
    short xOrg = in.readInt16();
    short yOrg = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    javaBuilder.xOrg(xOrg);
    javaBuilder.yOrg(yOrg);
    javaBuilder.width(width);
    javaBuilder.height(height);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt16(xOrg);
    out.writeInt16(yOrg);
    out.writeCard16(width);
    out.writeCard16(height);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ScreenInfoBuilder {
    public int getSize() {
      return 8;
    }
  }
}
