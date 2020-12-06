package com.github.moaxcp.x11client.display;

import com.github.moaxcp.x11client.protocol.xproto.CreateGCRequest;
import com.github.moaxcp.x11client.protocol.xproto.FreeGCRequest;

public class GraphicsContext extends Resource {
  GraphicsContext(Display display, int drawable, int background, int foreground) {
    super(display);
    display.send(CreateGCRequest.builder()
      .cid(getId())
      .drawable(drawable)
      .background(background)
      .foreground(foreground)
      .build());
  }

  public GraphicsContext(Display display, int drawable) {
    super(display);
    display.send(CreateGCRequest.builder()
      .cid(getId())
      .drawable(drawable)
      .build());
  }

  @Override
  public void close() {
    display.send(FreeGCRequest.builder()
      .gc(getId())
      .build());
  }
}
