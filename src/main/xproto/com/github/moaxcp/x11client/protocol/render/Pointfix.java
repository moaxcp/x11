package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Pointfix implements XStruct, RenderObject {
  private int x;

  private int y;

  public static Pointfix readPointfix(X11Input in) throws IOException {
    Pointfix.PointfixBuilder javaBuilder = Pointfix.builder();
    int x = in.readInt32();
    int y = in.readInt32();
    javaBuilder.x(x);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt32(x);
    out.writeInt32(y);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class PointfixBuilder {
    public int getSize() {
      return 8;
    }
  }
}
