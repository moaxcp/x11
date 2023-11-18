package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Arc implements XStruct, XprotoObject {
  private short x;

  private short y;

  private short width;

  private short height;

  private short angle1;

  private short angle2;

  public static Arc readArc(X11Input in) throws IOException {
    Arc.ArcBuilder javaBuilder = Arc.builder();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    short angle1 = in.readInt16();
    short angle2 = in.readInt16();
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.angle1(angle1);
    javaBuilder.angle2(angle2);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeInt16(angle1);
    out.writeInt16(angle2);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class ArcBuilder {
    public int getSize() {
      return 12;
    }
  }
}
