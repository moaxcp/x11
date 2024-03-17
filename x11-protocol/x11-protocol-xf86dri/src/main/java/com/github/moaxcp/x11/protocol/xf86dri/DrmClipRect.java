package com.github.moaxcp.x11.protocol.xf86dri;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DrmClipRect implements XStruct {
  public static final String PLUGIN_NAME = "xf86dri";

  private short x1;

  private short y1;

  private short x2;

  private short x3;

  public static DrmClipRect readDrmClipRect(X11Input in) throws IOException {
    DrmClipRect.DrmClipRectBuilder javaBuilder = DrmClipRect.builder();
    short x1 = in.readInt16();
    short y1 = in.readInt16();
    short x2 = in.readInt16();
    short x3 = in.readInt16();
    javaBuilder.x1(x1);
    javaBuilder.y1(y1);
    javaBuilder.x2(x2);
    javaBuilder.x3(x3);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt16(x1);
    out.writeInt16(y1);
    out.writeInt16(x2);
    out.writeInt16(x3);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DrmClipRectBuilder {
    public int getSize() {
      return 8;
    }
  }
}
