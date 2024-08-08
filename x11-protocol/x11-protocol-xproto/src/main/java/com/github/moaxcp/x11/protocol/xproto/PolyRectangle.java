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
public class PolyRectangle implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 67;

  private int drawable;

  private int gc;

  @NonNull
  private ImmutableList<Rectangle> rectangles;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PolyRectangle readPolyRectangle(X11Input in) throws IOException {
    PolyRectangle.PolyRectangleBuilder javaBuilder = PolyRectangle.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int gc = in.readCard32();
    javaStart += 4;
    MutableList<Rectangle> rectangles = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Rectangle baseObject = Rectangle.readRectangle(in);
      rectangles.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.rectangles(rectangles.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(gc);
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

  public static class PolyRectangleBuilder {
    public int getSize() {
      return 12 + XObject.sizeOf(rectangles);
    }
  }
}
