package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FreeGlyphSet implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";

  public static final byte OPCODE = 19;

  private int glyphset;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreeGlyphSet readFreeGlyphSet(X11Input in) throws IOException {
    FreeGlyphSet.FreeGlyphSetBuilder javaBuilder = FreeGlyphSet.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int glyphset = in.readCard32();
    javaBuilder.glyphset(glyphset);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(glyphset);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class FreeGlyphSetBuilder {
    public int getSize() {
      return 8;
    }
  }
}
