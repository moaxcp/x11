package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XError;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GlyphError implements XError {
  public static final String PLUGIN_NAME = "render";

  public static final byte CODE = 4;

  private byte firstErrorOffset;

  private short sequenceNumber;

  private short minorOpcode;

  private byte majorOpcode;

  @Override
  public byte getCode() {
    return (byte) (firstErrorOffset + CODE);
  }

  public static GlyphError readGlyphError(byte firstErrorOffset, X11Input in) throws IOException {
    GlyphError.GlyphErrorBuilder javaBuilder = GlyphError.builder();
    short sequenceNumber = in.readCard16();
    short minorOpcode = in.readCard16();
    byte majorOpcode = in.readCard8();
    byte[] pad5 = in.readPad(25);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.minorOpcode(minorOpcode);
    javaBuilder.majorOpcode(majorOpcode);

    javaBuilder.firstErrorOffset(firstErrorOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(getCode());
    out.writeCard16(sequenceNumber);
    out.writeCard16(minorOpcode);
    out.writeCard8(majorOpcode);
    out.writePad(25);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GlyphErrorBuilder {
    public int getSize() {
      return 32;
    }
  }
}
