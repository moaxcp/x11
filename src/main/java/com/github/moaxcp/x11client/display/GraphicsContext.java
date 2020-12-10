package com.github.moaxcp.x11client.display;

import com.github.moaxcp.x11client.protocol.xproto.CreateGC;
import com.github.moaxcp.x11client.protocol.xproto.FreeGC;

public class GraphicsContext extends Resource {
  GraphicsContext(Display display, int drawable, int background, int foreground) {
    super(display);
    display.send(CreateGC.builder()
      .cid(getId())
      .drawable(drawable)
      .background(background)
      .foreground(foreground)
      .build());
  }

  public GraphicsContext(Display display, int drawable) {
    super(display);
    display.send(CreateGC.builder()
      .cid(getId())
      .drawable(drawable)
      .build());
  }

  @Override
  public void close() {
    display.send(FreeGC.builder()
      .gc(getId())
      .build());
  }
}
