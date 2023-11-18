package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Rgb implements XStruct, XprotoObject {
  private short red;

  private short green;

  private short blue;

  public static Rgb readRgb(X11Input in) throws IOException {
    Rgb.RgbBuilder javaBuilder = Rgb.builder();
    short red = in.readCard16();
    short green = in.readCard16();
    short blue = in.readCard16();
    byte[] pad3 = in.readPad(2);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class RgbBuilder {
    public int getSize() {
      return 8;
    }
  }
}
