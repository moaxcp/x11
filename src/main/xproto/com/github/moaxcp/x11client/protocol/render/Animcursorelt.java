package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Animcursorelt implements XStruct, RenderObject {
  private int cursor;

  private int delay;

  public static Animcursorelt readAnimcursorelt(X11Input in) throws IOException {
    Animcursorelt.AnimcursoreltBuilder javaBuilder = Animcursorelt.builder();
    int cursor = in.readCard32();
    int delay = in.readCard32();
    javaBuilder.cursor(cursor);
    javaBuilder.delay(delay);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(cursor);
    out.writeCard32(delay);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class AnimcursoreltBuilder {
    public int getSize() {
      return 8;
    }
  }
}
