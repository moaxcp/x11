package com.github.moaxcp.x11client.experimental;

import lombok.Getter;

@Getter
public abstract class Drawable extends Resource {
  private int screen;
  private short x;
  private short y;
  private short width;
  private short height;
  private GraphicsContext gc;

  Drawable(Display display, int screen, short x, short y, short width, short height) {
    super(display);
    this.screen = screen;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public GraphicsContext getGc() {
    if(gc == null) {
      gc = new GraphicsContext(display, this);
    }
    return gc;
  }

  @Override
  public void close() {
    if(gc != null) {
      gc.close();
    }
  }
}
