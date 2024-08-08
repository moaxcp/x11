package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
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
public class FillRectangles implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";

  public static final byte OPCODE = 26;

  private byte op;

  private int dst;

  @NonNull
  private Color color;

  @NonNull
  private ImmutableList<Rectangle> rects;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FillRectangles readFillRectangles(X11Input in) throws IOException {
    FillRectangles.FillRectanglesBuilder javaBuilder = FillRectangles.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    byte op = in.readCard8();
    javaStart += 1;
    byte[] pad4 = in.readPad(3);
    javaStart += 3;
    int dst = in.readCard32();
    javaStart += 4;
    Color color = Color.readColor(in);
    javaStart += color.getSize();
    MutableList<Rectangle> rects = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Rectangle baseObject = Rectangle.readRectangle(in);
      rects.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.op(op);
    javaBuilder.dst(dst);
    javaBuilder.color(color);
    javaBuilder.rects(rects.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(op);
    out.writePad(3);
    out.writeCard32(dst);
    color.write(out);
    for(Rectangle t : rects) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 20 + XObject.sizeOf(rects);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class FillRectanglesBuilder {
    public FillRectangles.FillRectanglesBuilder op(PictOp op) {
      this.op = (byte) op.getValue();
      return this;
    }

    public FillRectangles.FillRectanglesBuilder op(byte op) {
      this.op = op;
      return this;
    }

    public int getSize() {
      return 20 + XObject.sizeOf(rects);
    }
  }
}
