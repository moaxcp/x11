package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class PolyLine implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 65;

  private byte coordinateMode;

  private int drawable;

  private int gc;

  @NonNull
  private ImmutableList<Point> points;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PolyLine readPolyLine(X11Input in) throws IOException {
    PolyLine.PolyLineBuilder javaBuilder = PolyLine.builder();
    int javaStart = 1;
    byte coordinateMode = in.readByte();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int gc = in.readCard32();
    javaStart += 4;
    MutableList<Point> points = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Point baseObject = Point.readPoint(in);
      points.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.coordinateMode(coordinateMode);
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.points(points.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeByte(coordinateMode);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(gc);
    for(Point t : points) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(points);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PolyLineBuilder {
    public PolyLine.PolyLineBuilder coordinateMode(CoordMode coordinateMode) {
      this.coordinateMode = (byte) coordinateMode.getValue();
      return this;
    }

    public PolyLine.PolyLineBuilder coordinateMode(byte coordinateMode) {
      this.coordinateMode = coordinateMode;
      return this;
    }

    public int getSize() {
      return 12 + XObject.sizeOf(points);
    }
  }
}
