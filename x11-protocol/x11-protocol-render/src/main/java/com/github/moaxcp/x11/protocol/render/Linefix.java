package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Linefix implements XStruct {
  public static final String PLUGIN_NAME = "render";

  @NonNull
  private Pointfix p1;

  @NonNull
  private Pointfix p2;

  public static Linefix readLinefix(X11Input in) throws IOException {
    Linefix.LinefixBuilder javaBuilder = Linefix.builder();
    Pointfix p1 = Pointfix.readPointfix(in);
    Pointfix p2 = Pointfix.readPointfix(in);
    javaBuilder.p1(p1);
    javaBuilder.p2(p2);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    p1.write(out);
    p2.write(out);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class LinefixBuilder {
    public int getSize() {
      return 16;
    }
  }
}
