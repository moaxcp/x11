package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CompositeGlyphs32 implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";

  public static final byte OPCODE = 25;

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

  public static CompositeGlyphs32 readCompositeGlyphs32(X11Input in) throws IOException {
    CompositeGlyphs32.CompositeGlyphs32Builder javaBuilder = CompositeGlyphs32.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    byte op = in.readCard8();
    javaStart += 1;
    byte[] pad4 = in.readPad(3);
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
    List<Byte> glyphcmds = in.readByte(Short.toUnsignedInt(length) - javaStart);
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
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(op);
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
    return 28 + 1 * glyphcmds.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CompositeGlyphs32Builder {
    public CompositeGlyphs32.CompositeGlyphs32Builder op(PictOp op) {
      this.op = (byte) op.getValue();
      return this;
    }

    public CompositeGlyphs32.CompositeGlyphs32Builder op(byte op) {
      this.op = op;
      return this;
    }

    public int getSize() {
      return 28 + 1 * glyphcmds.size();
    }
  }
}
