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
public class FreeGlyphs implements OneWayRequest, RenderObject {
  public static final byte OPCODE = 22;

  private int glyphset;

  @NonNull
  private List<Integer> glyphs;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreeGlyphs readFreeGlyphs(X11Input in) throws IOException {
    FreeGlyphs.FreeGlyphsBuilder javaBuilder = FreeGlyphs.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int glyphset = in.readCard32();
    javaStart += 4;
    List<Integer> glyphs = in.readCard32(javaStart - length);
    javaBuilder.glyphset(glyphset);
    javaBuilder.glyphs(glyphs);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(glyphset);
    out.writeCard32(glyphs);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 4 * glyphs.size();
  }

  public static class FreeGlyphsBuilder {
    public int getSize() {
      return 8 + 4 * glyphs.size();
    }
  }
}
