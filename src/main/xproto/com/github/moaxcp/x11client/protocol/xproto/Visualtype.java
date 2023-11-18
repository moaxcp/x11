package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Visualtype implements XStruct, XprotoObject {
  private int visualId;

  private byte clazz;

  private byte bitsPerRgbValue;

  private short colormapEntries;

  private int redMask;

  private int greenMask;

  private int blueMask;

  public static Visualtype readVisualtype(X11Input in) throws IOException {
    Visualtype.VisualtypeBuilder javaBuilder = Visualtype.builder();
    int visualId = in.readCard32();
    byte clazz = in.readCard8();
    byte bitsPerRgbValue = in.readCard8();
    short colormapEntries = in.readCard16();
    int redMask = in.readCard32();
    int greenMask = in.readCard32();
    int blueMask = in.readCard32();
    byte[] pad7 = in.readPad(4);
    javaBuilder.visualId(visualId);
    javaBuilder.clazz(clazz);
    javaBuilder.bitsPerRgbValue(bitsPerRgbValue);
    javaBuilder.colormapEntries(colormapEntries);
    javaBuilder.redMask(redMask);
    javaBuilder.greenMask(greenMask);
    javaBuilder.blueMask(blueMask);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(visualId);
    out.writeCard8(clazz);
    out.writeCard8(bitsPerRgbValue);
    out.writeCard16(colormapEntries);
    out.writeCard32(redMask);
    out.writeCard32(greenMask);
    out.writeCard32(blueMask);
    out.writePad(4);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public static class VisualtypeBuilder {
    public Visualtype.VisualtypeBuilder clazz(VisualClass clazz) {
      this.clazz = (byte) clazz.getValue();
      return this;
    }

    public Visualtype.VisualtypeBuilder clazz(byte clazz) {
      this.clazz = clazz;
      return this;
    }

    public int getSize() {
      return 24;
    }
  }
}
