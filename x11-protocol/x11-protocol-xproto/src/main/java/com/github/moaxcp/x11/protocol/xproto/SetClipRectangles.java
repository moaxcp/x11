package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetClipRectangles implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 59;

  private byte ordering;

  private int gc;

  private short clipXOrigin;

  private short clipYOrigin;

  @NonNull
  private List<Rectangle> rectangles;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetClipRectangles readSetClipRectangles(X11Input in) throws IOException {
    SetClipRectangles.SetClipRectanglesBuilder javaBuilder = SetClipRectangles.builder();
    int javaStart = 1;
    byte ordering = in.readByte();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int gc = in.readCard32();
    javaStart += 4;
    short clipXOrigin = in.readInt16();
    javaStart += 2;
    short clipYOrigin = in.readInt16();
    javaStart += 2;
    List<Rectangle> rectangles = new ArrayList<>(Short.toUnsignedInt(length) - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Rectangle baseObject = Rectangle.readRectangle(in);
      rectangles.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.ordering(ordering);
    javaBuilder.gc(gc);
    javaBuilder.clipXOrigin(clipXOrigin);
    javaBuilder.clipYOrigin(clipYOrigin);
    javaBuilder.rectangles(rectangles);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeByte(ordering);
    out.writeCard16((short) getLength());
    out.writeCard32(gc);
    out.writeInt16(clipXOrigin);
    out.writeInt16(clipYOrigin);
    for(Rectangle t : rectangles) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(rectangles);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetClipRectanglesBuilder {
    public SetClipRectangles.SetClipRectanglesBuilder ordering(ClipOrdering ordering) {
      this.ordering = (byte) ordering.getValue();
      return this;
    }

    public SetClipRectangles.SetClipRectanglesBuilder ordering(byte ordering) {
      this.ordering = ordering;
      return this;
    }

    public int getSize() {
      return 12 + XObject.sizeOf(rectangles);
    }
  }
}
