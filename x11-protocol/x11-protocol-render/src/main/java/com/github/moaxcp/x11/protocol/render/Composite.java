package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Composite implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";

  public static final byte OPCODE = 8;

  private byte op;

  private int src;

  private int mask;

  private int dst;

  private short srcX;

  private short srcY;

  private short maskX;

  private short maskY;

  private short dstX;

  private short dstY;

  private short width;

  private short height;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Composite readComposite(X11Input in) throws IOException {
    Composite.CompositeBuilder javaBuilder = Composite.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte op = in.readCard8();
    byte[] pad4 = in.readPad(3);
    int src = in.readCard32();
    int mask = in.readCard32();
    int dst = in.readCard32();
    short srcX = in.readInt16();
    short srcY = in.readInt16();
    short maskX = in.readInt16();
    short maskY = in.readInt16();
    short dstX = in.readInt16();
    short dstY = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    javaBuilder.op(op);
    javaBuilder.src(src);
    javaBuilder.mask(mask);
    javaBuilder.dst(dst);
    javaBuilder.srcX(srcX);
    javaBuilder.srcY(srcY);
    javaBuilder.maskX(maskX);
    javaBuilder.maskY(maskY);
    javaBuilder.dstX(dstX);
    javaBuilder.dstY(dstY);
    javaBuilder.width(width);
    javaBuilder.height(height);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(op);
    out.writePad(3);
    out.writeCard32(src);
    out.writeCard32(mask);
    out.writeCard32(dst);
    out.writeInt16(srcX);
    out.writeInt16(srcY);
    out.writeInt16(maskX);
    out.writeInt16(maskY);
    out.writeInt16(dstX);
    out.writeInt16(dstY);
    out.writeCard16(width);
    out.writeCard16(height);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CompositeBuilder {
    public Composite.CompositeBuilder op(PictOp op) {
      this.op = (byte) op.getValue();
      return this;
    }

    public Composite.CompositeBuilder op(byte op) {
      this.op = op;
      return this;
    }

    public int getSize() {
      return 36;
    }
  }
}
