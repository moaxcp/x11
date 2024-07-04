package com.github.moaxcp.x11.toolkit;

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
    if (gc == null) {
      gc = new GraphicsContext(display, this);
    }
    return gc;
  }

  @Override
  public void close() {
    if (gc != null) {
      gc.close();
    }
  }

  public int getScreen() {
    return this.screen;
  }

  public short getX() {
    return this.x;
  }

  public short getY() {
    return this.y;
  }

  public short getWidth() {
    return this.width;
  }

  public short getHeight() {
    return this.height;
  }
}
