package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.xproto.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11client.Utilities.byteArrayToList;
import static com.github.moaxcp.x11client.Utilities.stringToByteList;

public class X11ClientIT {

  @Test
  void clientTest() throws IOException {
    try(X11Client client = X11Client.connect()) {
      CreateWindow window = CreateWindow.builder()
        .depth(client.getDepth(0))
        .wid(client.nextResourceId())
        .parent(client.getRoot(0))
        .x((short) 10)
        .y((short) 10)
        .width((short) 600)
        .height((short) 480)
        .borderWidth((short) 5)
        .clazz(WindowClass.COPY_FROM_PARENT)
        .visual(client.getVisualId(0))
        .backgroundPixel(client.getWhitePixel(0))
        .borderPixel(client.getBlackPixel(0))
        .eventMaskEnable(EventMask.EXPOSURE, EventMask.KEY_PRESS)
        .build();
      client.send(window);
      InternAtomReply wmProtocols = client.send(InternAtom.builder().name(stringToByteList("WM_PROTOCOLS")).nameLen((short) "WM_PROTOCOLS".length()).build());
      InternAtomReply deleteAtom = client.send(InternAtom.builder().name(stringToByteList("WM_DELETE_WINDOW")).nameLen((short) "WM_DELETE_WINDOW".length()).build());
      client.send(ChangeProperty.builder()
        .window(window.getWid())
        .property(wmProtocols.getAtom())
        .type(Atom.ATOM.getValue())
        .format((byte) 32)
        .mode(PropMode.REPLACE)
        .data(byteArrayToList(ByteBuffer.allocate(4).putInt(deleteAtom.getAtom()).array()))
        .dataLen(1)
        .build());
      client.send(MapWindow.builder()
        .window(window.getWid())
        .build());
      CreateGC gc = CreateGC.builder()
        .cid(client.nextResourceId())
        .drawable(window.getWid())
        .background(client.getWhitePixel(0))
        .foreground(client.getBlackPixel(0))
        .build();
      client.send(gc);
      while(true) {
        XEvent event = client.getNextEvent();
        if(event instanceof ExposeEvent) {
          List<Rectangle> rectangles = new ArrayList<>();
          rectangles.add(Rectangle.builder()
            .x((short) 20)
            .y((short) 20)
            .width((short) 10)
            .height((short) 10)
            .build());
          client.send(PolyFillRectangle.builder()
            .drawable(window.getWid())
            .gc(gc.getCid())
            .rectangles(rectangles)
            .build());
          client.send(ImageText8.builder()
            .drawable(window.getWid())
            .gc(gc.getCid())
            .stringLen((byte) "Hello World!".length())
            .string(stringToByteList("Hello World!"))
            .x((short) 10)
            .y((short) 50)
            .build());
        } else if(event instanceof KeyPressEvent) {
          break;
        } else if(event instanceof ClientMessageEvent) {
          ClientMessageEvent clientMessage = (ClientMessageEvent) event;
          if(clientMessage.getFormat() == 32) {
            ClientMessageData32 data = (ClientMessageData32) clientMessage.getData();
            if(data.getData32().get(0) == deleteAtom.getAtom()) {
              break;
            }
          }
        } else {
          throw new IllegalStateException(event.toString());
        }
      }
    }
  }
}
