package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.Point;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class Outline implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte cornerRadius;

  @NonNull
  private ImmutableList<Point> points;

  public static Outline readOutline(X11Input in) throws IOException {
    Outline.OutlineBuilder javaBuilder = Outline.builder();
    byte nPoints = in.readCard8();
    byte cornerRadius = in.readCard8();
    byte[] pad2 = in.readPad(2);
    MutableList<Point> points = Lists.mutable.withInitialCapacity(Byte.toUnsignedInt(nPoints));
    for(int i = 0; i < Byte.toUnsignedInt(nPoints); i++) {
      points.add(Point.readPoint(in));
    }
    javaBuilder.cornerRadius(cornerRadius);
    javaBuilder.points(points.toImmutable());
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class OutlineBuilder {
    public int getSize() {
      return 4 + XObject.sizeOf(points);
    }
  }
}
