package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XStruct;
import com.github.moaxcp.x11client.protocol.xproto.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Outline implements XStruct, XkbObject {
  private byte cornerRadius;

  @NonNull
  private List<Point> points;

  public static Outline readOutline(X11Input in) throws IOException {
    Outline.OutlineBuilder javaBuilder = Outline.builder();
    byte nPoints = in.readCard8();
    byte cornerRadius = in.readCard8();
    byte[] pad2 = in.readPad(2);
    List<Point> points = new ArrayList<>(Byte.toUnsignedInt(nPoints));
    for(int i = 0; i < Byte.toUnsignedInt(nPoints); i++) {
      points.add(Point.readPoint(in));
    }
    javaBuilder.cornerRadius(cornerRadius);
    javaBuilder.points(points);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    byte nPoints = (byte) points.size();
    out.writeCard8(nPoints);
    out.writeCard8(cornerRadius);
    out.writePad(2);
    for(Point t : points) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 4 + XObject.sizeOf(points);
  }

  public static class OutlineBuilder {
    public int getSize() {
      return 4 + XObject.sizeOf(points);
    }
  }
}
