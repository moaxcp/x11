package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Coloritem implements XStruct, XprotoObject {
  private int pixel;

  private short red;

  private short green;

  private short blue;

  private byte flags;

  public static Coloritem readColoritem(X11Input in) throws IOException {
    Coloritem.ColoritemBuilder javaBuilder = Coloritem.builder();
    int pixel = in.readCard32();
    short red = in.readCard16();
    short green = in.readCard16();
    short blue = in.readCard16();
    byte flags = in.readByte();
    byte[] pad5 = in.readPad(1);
    javaBuilder.pixel(pixel);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    javaBuilder.flags(flags);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(pixel);
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writeByte(flags);
    out.writePad(1);
  }

  public boolean isFlagsEnabled(@NonNull ColorFlag... maskEnums) {
    for(ColorFlag m : maskEnums) {
      if(!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class ColoritemBuilder {
    public boolean isFlagsEnabled(@NonNull ColorFlag... maskEnums) {
      for(ColorFlag m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public Coloritem.ColoritemBuilder flagsEnable(ColorFlag... maskEnums) {
      for(ColorFlag m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public Coloritem.ColoritemBuilder flagsDisable(ColorFlag... maskEnums) {
      for(ColorFlag m : maskEnums) {
        flags((byte) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
