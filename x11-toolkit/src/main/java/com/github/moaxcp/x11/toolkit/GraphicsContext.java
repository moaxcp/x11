package com.github.moaxcp.x11.toolkit;

import com.github.moaxcp.x11.protocol.Utilities;
import com.github.moaxcp.x11.protocol.xproto.*;
import org.eclipse.collections.impl.factory.Lists;

public class GraphicsContext extends Resource {
  private final int drawable;
  private int background;
  private int foreground;

  public GraphicsContext(Display display, Drawable drawable) {
    super(display);
    this.drawable = drawable.getId();
    this.background = display.getWhitePixel(drawable.getScreen());
    this.foreground = display.getBlackPixel(drawable.getScreen());
    display.send(CreateGC.builder()
            .cid(getId())
            .drawable(this.drawable)
            .background(background)
            .foreground(foreground)
            .build());
  }

  public void polyFillRectangle(Rectangle rectangle) {
    display.send(PolyFillRectangle.builder()
            .drawable(drawable)
            .gc(getId())
            .rectangles(Lists.immutable.of(rectangle))
            .build());
  }

  public void imageText8(short x, short y, String text) {
    display.send(ImageText8.builder()
            .drawable(drawable)
            .gc(getId())
            .x(x)
            .y(y)
            .string(Utilities.toByteList(text))
            .build());
  }

  @Override
  public void close() {
    display.send(FreeGC.builder()
            .gc(getId())
            .build());
  }

  public int getDrawable() {
    return this.drawable;
  }

  public int getBackground() {
    return this.background;
  }

  public int getForeground() {
    return this.foreground;
  }
}
