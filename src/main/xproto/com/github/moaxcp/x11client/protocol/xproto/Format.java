package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Format implements XStruct, XprotoObject {
  private byte depth;

  private byte bitsPerPixel;

  private byte scanlinePad;

  public static Format readFormat(X11Input in) throws IOException {
    Format.FormatBuilder javaBuilder = Format.builder();
    byte depth = in.readCard8();
    byte bitsPerPixel = in.readCard8();
    byte scanlinePad = in.readCard8();
    byte[] pad3 = in.readPad(5);
    javaBuilder.depth(depth);
    javaBuilder.bitsPerPixel(bitsPerPixel);
    javaBuilder.scanlinePad(scanlinePad);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(depth);
    out.writeCard8(bitsPerPixel);
    out.writeCard8(scanlinePad);
    out.writePad(5);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class FormatBuilder {
    public int getSize() {
      return 8;
    }
  }
}
