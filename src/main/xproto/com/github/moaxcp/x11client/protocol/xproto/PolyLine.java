package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PolyLine implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 65;

  private byte coordinateMode;

  private int drawable;

  private int gc;

  @NonNull
  private List<Point> points;

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
    List<Point> points = new ArrayList<>(length - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Point baseObject = Point.readPoint(in);
      points.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.coordinateMode(coordinateMode);
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.points(points);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
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
