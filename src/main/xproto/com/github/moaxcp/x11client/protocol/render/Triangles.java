package com.github.moaxcp.x11client.protocol.render;

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
public class Triangles implements OneWayRequest, RenderObject {
  public static final byte OPCODE = 11;

  private byte op;

  private int src;

  private int dst;

  private int maskFormat;

  private short srcX;

  private short srcY;

  @NonNull
  private List<Triangle> triangles;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Triangles readTriangles(X11Input in) throws IOException {
    Triangles.TrianglesBuilder javaBuilder = Triangles.builder();
    int javaStart = 1;
    byte op = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    byte[] pad3 = in.readPad(3);
    javaStart += 3;
    int src = in.readCard32();
    javaStart += 4;
    int dst = in.readCard32();
    javaStart += 4;
    int maskFormat = in.readCard32();
    javaStart += 4;
    short srcX = in.readInt16();
    javaStart += 2;
    short srcY = in.readInt16();
    javaStart += 2;
    List<Triangle> triangles = new ArrayList<>(length - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Triangle baseObject = Triangle.readTriangle(in);
      triangles.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.op(op);
    javaBuilder.src(src);
    javaBuilder.dst(dst);
    javaBuilder.maskFormat(maskFormat);
    javaBuilder.srcX(srcX);
    javaBuilder.srcY(srcY);
    javaBuilder.triangles(triangles);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(op);
    out.writeCard16((short) getLength());
    out.writePad(3);
    out.writeCard32(src);
    out.writeCard32(dst);
    out.writeCard32(maskFormat);
    out.writeInt16(srcX);
    out.writeInt16(srcY);
    for(Triangle t : triangles) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 23 + XObject.sizeOf(triangles);
  }

  public static class TrianglesBuilder {
    public Triangles.TrianglesBuilder op(PictOp op) {
      this.op = (byte) op.getValue();
      return this;
    }

    public Triangles.TrianglesBuilder op(byte op) {
      this.op = op;
      return this;
    }

    public int getSize() {
      return 23 + XObject.sizeOf(triangles);
    }
  }
}
