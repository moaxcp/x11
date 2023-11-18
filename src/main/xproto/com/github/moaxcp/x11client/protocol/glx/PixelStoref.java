package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PixelStoref implements OneWayRequest, GlxObject {
  public static final byte OPCODE = 109;

  private int contextTag;

  private int pname;

  private float datum;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PixelStoref readPixelStoref(X11Input in) throws IOException {
    PixelStoref.PixelStorefBuilder javaBuilder = PixelStoref.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int pname = in.readCard32();
    float datum = in.readFloat();
    javaBuilder.contextTag(contextTag);
    javaBuilder.pname(pname);
    javaBuilder.datum(datum);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(pname);
    out.writeFloat(datum);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class PixelStorefBuilder {
    public int getSize() {
      return 16;
    }
  }
}
