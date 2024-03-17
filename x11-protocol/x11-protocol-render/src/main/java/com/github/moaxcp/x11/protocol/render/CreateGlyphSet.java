package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateGlyphSet implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";

  public static final byte OPCODE = 17;

  private int gsid;

  private int format;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateGlyphSet readCreateGlyphSet(X11Input in) throws IOException {
    CreateGlyphSet.CreateGlyphSetBuilder javaBuilder = CreateGlyphSet.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int gsid = in.readCard32();
    int format = in.readCard32();
    javaBuilder.gsid(gsid);
    javaBuilder.format(format);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(gsid);
    out.writeCard32(format);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateGlyphSetBuilder {
    public int getSize() {
      return 12;
    }
  }
}
