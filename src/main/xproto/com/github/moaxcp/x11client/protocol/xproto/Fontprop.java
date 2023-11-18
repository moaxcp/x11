package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Fontprop implements XStruct, XprotoObject {
  private int name;

  private int value;

  public static Fontprop readFontprop(X11Input in) throws IOException {
    Fontprop.FontpropBuilder javaBuilder = Fontprop.builder();
    int name = in.readCard32();
    int value = in.readCard32();
    javaBuilder.name(name);
    javaBuilder.value(value);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(name);
    out.writeCard32(value);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class FontpropBuilder {
    public int getSize() {
      return 8;
    }
  }
}
