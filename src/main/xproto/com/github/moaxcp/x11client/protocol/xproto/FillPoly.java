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
public class FillPoly implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 69;

  private int drawable;

  private int gc;

  private byte shape;

  private byte coordinateMode;

  @NonNull
  private List<Point> points;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FillPoly readFillPoly(X11Input in) throws IOException {
    FillPoly.FillPolyBuilder javaBuilder = FillPoly.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int gc = in.readCard32();
    javaStart += 4;
    byte shape = in.readCard8();
    javaStart += 1;
    byte coordinateMode = in.readCard8();
    javaStart += 1;
    byte[] pad7 = in.readPad(2);
    javaStart += 2;
    List<Point> points = new ArrayList<>(length - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Point baseObject = Point.readPoint(in);
      points.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.shape(shape);
    javaBuilder.coordinateMode(coordinateMode);
    javaBuilder.points(points);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(gc);
    out.writeCard8(shape);
    out.writeCard8(coordinateMode);
    out.writePad(2);
    for(Point t : points) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + XObject.sizeOf(points);
  }

  public static class FillPolyBuilder {
    public FillPoly.FillPolyBuilder shape(PolyShape shape) {
      this.shape = (byte) shape.getValue();
      return this;
    }

    public FillPoly.FillPolyBuilder shape(byte shape) {
      this.shape = shape;
      return this;
    }

    public FillPoly.FillPolyBuilder coordinateMode(CoordMode coordinateMode) {
      this.coordinateMode = (byte) coordinateMode.getValue();
      return this;
    }

    public FillPoly.FillPolyBuilder coordinateMode(byte coordinateMode) {
      this.coordinateMode = coordinateMode;
      return this;
    }

    public int getSize() {
      return 16 + XObject.sizeOf(points);
    }
  }
}
