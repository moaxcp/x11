package com.github.moaxcp.x11.toolkit;

import com.github.moaxcp.x11.protocol.xproto.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@Getter
public class Window extends Drawable {
  private byte depth;
  private int parent;
  private short borderWidth;
  private WindowClass clazz;
  private int visual;
  private int backgroundPixel;
  private int borderPixel;
  private List<BiConsumer<Window, ExposeEvent>> exposeEventConsumers = new ArrayList<>();
  private List<BiConsumer<Window, KeyPressEvent>> keyPressEventConsumers = new ArrayList<>();

  Window(Display display, int screen, short x, short y, short width, short height) {
    super(display, screen, x, y, width, height);
    depth = display.getDepth(screen);
    parent = display.getRoot(screen);
    this.borderWidth = 0;
    clazz = WindowClass.COPY_FROM_PARENT;
    visual = display.getVisualId(screen);
    backgroundPixel = display.getWhitePixel(screen);
    borderPixel = display.getBlackPixel(screen);
    display.send(CreateWindow.builder()
        .depth(depth)
        .wid(getId())
        .parent(parent)
        .x(x)
        .y(y)
        .width(width)
        .height(height)
        .borderWidth(borderWidth)
        .clazz(clazz)
        .visual(visual)
        .backgroundPixel(backgroundPixel)
        .borderPixel(borderPixel)
        .eventMaskEnable(EventMask.EXPOSURE)
        .eventMaskEnable(EventMask.KEY_PRESS)
        .build());
  }

  public void map() {
    display.send(MapWindow.builder()
      .window(getId())
      .build());
  }

  @Override
  public void close() {
    super.close();
    display.send(DestroyWindow.builder()
      .window(getId())
      .build());
    keyPressEventConsumers.clear();
    exposeEventConsumers.clear();
  }

  void exposeEvent(ExposeEvent event) {
    for(BiConsumer<Window, ExposeEvent> consumer : exposeEventConsumers) {
      consumer.accept(this, event);
    }
  }

  void keyPressEvent(KeyPressEvent event) {
    for(BiConsumer<Window, KeyPressEvent> consumer : keyPressEventConsumers) {
      consumer.accept(this, event);
    }
  }

  public void exposeEvent(BiConsumer<Window, ExposeEvent> consumer) {
    exposeEventConsumers.add(consumer);
  }

  public void keyPressEvent(BiConsumer<Window, KeyPressEvent> consumer) {
    keyPressEventConsumers.add(consumer);
  }
}
