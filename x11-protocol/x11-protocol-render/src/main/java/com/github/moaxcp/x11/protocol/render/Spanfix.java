package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Spanfix implements XStruct {
  public static final String PLUGIN_NAME = "render";

  private int l;

  private int r;

  private int y;

  public static Spanfix readSpanfix(X11Input in) throws IOException {
    Spanfix.SpanfixBuilder javaBuilder = Spanfix.builder();
    int l = in.readInt32();
    int r = in.readInt32();
    int y = in.readInt32();
    javaBuilder.l(l);
    javaBuilder.r(r);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt32(l);
    out.writeInt32(r);
    out.writeInt32(y);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SpanfixBuilder {
    public int getSize() {
      return 12;
    }
  }
}
