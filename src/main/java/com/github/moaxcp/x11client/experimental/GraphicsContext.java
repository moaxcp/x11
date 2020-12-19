package com.github.moaxcp.x11client.experimental;

import com.github.moaxcp.x11client.protocol.xproto.*;
import java.util.Collections;
import lombok.Getter;

import static com.github.moaxcp.x11client.protocol.Utilities.stringToByteList;

@Getter
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
      .rectangles(Collections.singletonList(rectangle))
      .build());
  }

  public void imageText8(short x, short y, String text) {
    display.send(ImageText8.builder()
      .drawable(drawable)
      .gc(getId())
      .x(x)
      .y(y)
      .stringLen((byte) text.length())
      .string(stringToByteList(text))
      .build());
  }

  @Override
  public void close() {
    display.send(FreeGC.builder()
      .gc(getId())
      .build());
  }
}
