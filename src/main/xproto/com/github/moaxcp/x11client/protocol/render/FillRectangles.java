package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.xproto.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class FillRectangles implements OneWayRequest, RenderObject {
  public static final byte OPCODE = 26;

  private byte op;

  private int dst;

  @NonNull
  private Color color;

  @NonNull
  private List<Rectangle> rects;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FillRectangles readFillRectangles(X11Input in) throws IOException {
    FillRectangles.FillRectanglesBuilder javaBuilder = FillRectangles.builder();
    int javaStart = 1;
    byte op = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    byte[] pad3 = in.readPad(3);
    javaStart += 3;
    int dst = in.readCard32();
    javaStart += 4;
    Color color = Color.readColor(in);
    javaStart += color.getSize();
    List<Rectangle> rects = new ArrayList<>(length - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Rectangle baseObject = Rectangle.readRectangle(in);
      rects.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.op(op);
    javaBuilder.dst(dst);
    javaBuilder.color(color);
    javaBuilder.rects(rects);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(op);
    out.writeCard16((short) getLength());
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
    return 19 + XObject.sizeOf(rects);
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
      return 19 + XObject.sizeOf(rects);
    }
  }
}
