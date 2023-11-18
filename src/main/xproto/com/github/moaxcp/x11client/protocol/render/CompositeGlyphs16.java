package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CompositeGlyphs16 implements OneWayRequest, RenderObject {
  public static final byte OPCODE = 24;

  private byte op;

  private int src;

  private int dst;

  private int maskFormat;

  private int glyphset;

  private short srcX;

  private short srcY;

  @NonNull
  private List<Byte> glyphcmds;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CompositeGlyphs16 readCompositeGlyphs16(X11Input in) throws IOException {
    CompositeGlyphs16.CompositeGlyphs16Builder javaBuilder = CompositeGlyphs16.builder();
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
    int glyphset = in.readCard32();
    javaStart += 4;
    short srcX = in.readInt16();
    javaStart += 2;
    short srcY = in.readInt16();
    javaStart += 2;
    List<Byte> glyphcmds = in.readByte(javaStart - length);
    javaBuilder.op(op);
    javaBuilder.src(src);
    javaBuilder.dst(dst);
    javaBuilder.maskFormat(maskFormat);
    javaBuilder.glyphset(glyphset);
    javaBuilder.srcX(srcX);
    javaBuilder.srcY(srcY);
    javaBuilder.glyphcmds(glyphcmds);
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
    out.writeCard32(glyphset);
    out.writeInt16(srcX);
    out.writeInt16(srcY);
    out.writeByte(glyphcmds);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 27 + 1 * glyphcmds.size();
  }

  public static class CompositeGlyphs16Builder {
    public CompositeGlyphs16.CompositeGlyphs16Builder op(PictOp op) {
      this.op = (byte) op.getValue();
      return this;
    }

    public CompositeGlyphs16.CompositeGlyphs16Builder op(byte op) {
      this.op = op;
      return this;
    }

    public int getSize() {
      return 27 + 1 * glyphcmds.size();
    }
  }
}
