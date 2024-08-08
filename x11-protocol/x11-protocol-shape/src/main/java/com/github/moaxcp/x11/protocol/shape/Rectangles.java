package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.xproto.ClipOrdering;
import com.github.moaxcp.x11.protocol.xproto.Rectangle;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class Rectangles implements OneWayRequest {
  public static final String PLUGIN_NAME = "shape";

  public static final byte OPCODE = 1;

  private byte operation;

  private byte destinationKind;

  private byte ordering;

  private int destinationWindow;

  private short xOffset;

  private short yOffset;

  @NonNull
  private ImmutableList<Rectangle> rectangles;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Rectangles readRectangles(X11Input in) throws IOException {
    Rectangles.RectanglesBuilder javaBuilder = Rectangles.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    byte operation = in.readCard8();
    javaStart += 1;
    byte destinationKind = in.readCard8();
    javaStart += 1;
    byte ordering = in.readByte();
    javaStart += 1;
    byte[] pad6 = in.readPad(1);
    javaStart += 1;
    int destinationWindow = in.readCard32();
    javaStart += 4;
    short xOffset = in.readInt16();
    javaStart += 2;
    short yOffset = in.readInt16();
    javaStart += 2;
    MutableList<Rectangle> rectangles = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
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
    javaBuilder.rectangles(rectangles.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(operation);
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
    return 16 + XObject.sizeOf(rectangles);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
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
      return 16 + XObject.sizeOf(rectangles);
    }
  }
}
