package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Format implements XStruct {
  public static final String PLUGIN_NAME = "xproto";

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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class FormatBuilder {
    public int getSize() {
      return 8;
    }
  }
}
