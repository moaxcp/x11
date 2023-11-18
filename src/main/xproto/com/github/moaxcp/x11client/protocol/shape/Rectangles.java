package com.github.moaxcp.x11client.protocol.shape;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.xproto.ClipOrdering;
import com.github.moaxcp.x11client.protocol.xproto.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Rectangles implements OneWayRequest, ShapeObject {
  public static final byte OPCODE = 1;

  private byte operation;

  private byte destinationKind;

  private byte ordering;

  private int destinationWindow;

  private short xOffset;

  private short yOffset;

  @NonNull
  private List<Rectangle> rectangles;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Rectangles readRectangles(X11Input in) throws IOException {
    Rectangles.RectanglesBuilder javaBuilder = Rectangles.builder();
    int javaStart = 1;
    byte operation = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    byte destinationKind = in.readCard8();
    javaStart += 1;
    byte ordering = in.readByte();
    javaStart += 1;
    byte[] pad5 = in.readPad(1);
    javaStart += 1;
    int destinationWindow = in.readCard32();
    javaStart += 4;
    short xOffset = in.readInt16();
    javaStart += 2;
    short yOffset = in.readInt16();
    javaStart += 2;
    List<Rectangle> rectangles = new ArrayList<>(length - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Rectangle baseObject = Rectangle.readRectangle(in);
      rectangles.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.operation(operation);
    javaBuilder.destinationKind(destinationKind);
    javaBuilder.ordering(ordering);
    javaBuilder.destinationWindow(destinationWindow);
    javaBuilder.xOffset(xOffset);
    javaBuilder.yOffset(yOffset);
    javaBuilder.rectangles(rectangles);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(operation);
    out.writeCard16((short) getLength());
    out.writeCard8(destinationKind);
    out.writeByte(ordering);
    out.writePad(1);
    out.writeCard32(destinationWindow);
    out.writeInt16(xOffset);
    out.writeInt16(yOffset);
    for(Rectangle t : rectangles) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 15 + XObject.sizeOf(rectangles);
  }

  public static class RectanglesBuilder {
    public Rectangles.RectanglesBuilder operation(So operation) {
      this.operation = (byte) operation.getValue();
      return this;
    }

    public Rectangles.RectanglesBuilder operation(byte operation) {
      this.operation = operation;
      return this;
    }

    public Rectangles.RectanglesBuilder destinationKind(Sk destinationKind) {
      this.destinationKind = (byte) destinationKind.getValue();
      return this;
    }

    public Rectangles.RectanglesBuilder destinationKind(byte destinationKind) {
      this.destinationKind = destinationKind;
      return this;
    }

    public Rectangles.RectanglesBuilder ordering(ClipOrdering ordering) {
      this.ordering = (byte) ordering.getValue();
      return this;
    }

    public Rectangles.RectanglesBuilder ordering(byte ordering) {
      this.ordering = ordering;
      return this;
    }

    public int getSize() {
      return 15 + XObject.sizeOf(rectangles);
    }
  }
}
