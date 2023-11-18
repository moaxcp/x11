package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Point implements XStruct, XprotoObject {
  private short x;

  private short y;

  public static Point readPoint(X11Input in) throws IOException {
    Point.PointBuilder javaBuilder = Point.builder();
    short x = in.readInt16();
    short y = in.readInt16();
    javaBuilder.x(x);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt16(x);
    out.writeInt16(y);
  }

  @Override
  public int getSize() {
    return 4;
  }

  public static class PointBuilder {
    public int getSize() {
      return 4;
    }
  }
}
