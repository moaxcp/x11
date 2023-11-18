package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FreeGlyphSet implements OneWayRequest, RenderObject {
  public static final byte OPCODE = 19;

  private int glyphset;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreeGlyphSet readFreeGlyphSet(X11Input in) throws IOException {
    FreeGlyphSet.FreeGlyphSetBuilder javaBuilder = FreeGlyphSet.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int glyphset = in.readCard32();
    javaBuilder.glyphset(glyphset);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(glyphset);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class FreeGlyphSetBuilder {
    public int getSize() {
      return 8;
    }
  }
}
