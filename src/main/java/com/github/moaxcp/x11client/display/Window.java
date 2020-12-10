package com.github.moaxcp.x11client.display;

import com.github.moaxcp.x11client.protocol.xproto.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Window extends Resource {
  private List<BiConsumer<Display, ExposeEvent>> exposeEventConsumers = new ArrayList<>();
  private List<BiConsumer<Display, KeyPressEvent>> keyPressEventConsumers = new ArrayList<>();

  Window(Display display, int screen, short x, short y, short width, short height, short borderWidth) {
    super(display);
    display.send(CreateWindow.builder()
        .depth(display.getDepth(screen))
        .wid(display.nextResourceId())
        .parent(display.getRoot(screen))
        .x(x)
        .y(y)
        .width(width)
        .height(height)
        .borderWidth(borderWidth)
        .clazz(WindowClass.COPY_FROM_PARENT)
        .visual(display.getVisualId(screen))
        .backgroundPixel(display.getWhitePixel(screen))
        .borderPixel(display.getBlackPixel(screen))
        .eventMaskEnable(EventMask.EXPOSURE)
        .eventMaskEnable(EventMask.KEY_PRESS)
        .build());
  }

  public void map() {
    display.send(MapWindow.builder()
      .window(getId())
      .build());
  }

  public GraphicsContext createGC() {
    return new GraphicsContext(display, getId());
  }

  @Override
  public void close() {
    display.send(DestroyWindow.builder()
      .window(getId())
      .build());
  }

  void exposeEvent(ExposeEvent event) {
    for(BiConsumer<Display, ExposeEvent> consumer : exposeEventConsumers) {
      consumer.accept(display, event);
    }
  }

  void keyPressEvent(KeyPressEvent event) {
    for(BiConsumer<Display, KeyPressEvent> consumer : keyPressEventConsumers) {
      consumer.accept(display, event);
    }
  }

  public void exposeEvent(BiConsumer<Display, ExposeEvent> consumer) {
    exposeEventConsumers.add(consumer);
  }

  public void keyPressEvent(BiConsumer<Display, KeyPressEvent> consumer) {
    keyPressEventConsumers.add(consumer);
  }
}
