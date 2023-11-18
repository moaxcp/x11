package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateGlyphSet implements OneWayRequest, RenderObject {
  public static final byte OPCODE = 17;

  private int gsid;

  private int format;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateGlyphSet readCreateGlyphSet(X11Input in) throws IOException {
    CreateGlyphSet.CreateGlyphSetBuilder javaBuilder = CreateGlyphSet.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int gsid = in.readCard32();
    int format = in.readCard32();
    javaBuilder.gsid(gsid);
    javaBuilder.format(format);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(gsid);
    out.writeCard32(format);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class CreateGlyphSetBuilder {
    public int getSize() {
      return 12;
    }
  }
}
