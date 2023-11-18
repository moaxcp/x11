package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Directformat implements XStruct, RenderObject {
  private short redShift;

  private short redMask;

  private short greenShift;

  private short greenMask;

  private short blueShift;

  private short blueMask;

  private short alphaShift;

  private short alphaMask;

  public static Directformat readDirectformat(X11Input in) throws IOException {
    Directformat.DirectformatBuilder javaBuilder = Directformat.builder();
    short redShift = in.readCard16();
    short redMask = in.readCard16();
    short greenShift = in.readCard16();
    short greenMask = in.readCard16();
    short blueShift = in.readCard16();
    short blueMask = in.readCard16();
    short alphaShift = in.readCard16();
    short alphaMask = in.readCard16();
    javaBuilder.redShift(redShift);
    javaBuilder.redMask(redMask);
    javaBuilder.greenShift(greenShift);
    javaBuilder.greenMask(greenMask);
    javaBuilder.blueShift(blueShift);
    javaBuilder.blueMask(blueMask);
    javaBuilder.alphaShift(alphaShift);
    javaBuilder.alphaMask(alphaMask);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(redShift);
    out.writeCard16(redMask);
    out.writeCard16(greenShift);
    out.writeCard16(greenMask);
    out.writeCard16(blueShift);
    out.writeCard16(blueMask);
    out.writeCard16(alphaShift);
    out.writeCard16(alphaMask);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class DirectformatBuilder {
    public int getSize() {
      return 16;
    }
  }
}
