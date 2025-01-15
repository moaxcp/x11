package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.xproto.*;
import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;

public class BitmapWindow {
  public static void main(String... args) throws IOException {
    try(X11Client client = X11Client.connect()) {
      int wid = client.nextResourceId();
      client.send(CreateWindow.builder()
          .wid(wid)
          .depth(client.getDefaultDepth())
          .parent(client.getDefaultRoot())
          .x((short) 10)
          .y((short) 10)
          .width((short) 300)
          .height((short) 300)
          .clazz(WindowClass.COPY_FROM_PARENT)
          .visual(client.getDefaultVisualId())
          .backgroundPixel(client.getDefaultWhitePixel())
          .borderPixel(client.getDefaultBlackPixel())
          .eventMaskEnable(EventMask.EXPOSURE)
          .build());
      client.setWindowName(wid, "Hello World!");
      int deleteAtom = client.getAtom("WM_DELETE_WINDOW").getId();
      client.setWMProtocols(wid, deleteAtom);
      client.mapWindow(wid);
      var pid = client.nextResourceId();
      client.send(CreatePixmap.builder()
          .pid(pid)
          .drawable(wid)
          .width((short) 200)
          .height((short) 200)
          .depth((byte) 1)
          .build());
      var gc = client.nextResourceId();
      client.send(CreateGC.builder()
          .cid(gc)
          .drawable(pid)
          .foreground(client.getBlackPixel(client.getDefaultScreenNumber()))
          .background(client.getWhitePixel(client.getDefaultScreenNumber()))
          .build());
      var bitmap = client.createBitmap((short) 200, (short) 200);
      var editor = bitmap.editor();
      editor.set(199, 199);
      client.send(PutImage.builder()
          .drawable(pid)
          .gc(gc)
          .depth((byte) 1)
          .dstX((short) 10)
          .dstY((short) 10)
          .data(editor.create().getData())
          .build());
      while(true) {
        XEvent event = client.getNextEvent();
        if(event instanceof ExposeEvent) {

        } else if(event instanceof KeyPressEvent) {
          break;
        } else if(event instanceof ClientMessageEvent) {
          ClientMessageEvent clientMessage = (ClientMessageEvent) event;
          if(clientMessage.getFormat() == 32) {
            ClientMessageData32 data = (ClientMessageData32) clientMessage.getData();
            if(data.getData32().get(0) == deleteAtom) {
              break;
            }
          }
        }
      }
      client.freeGC(gc);
    }
  }
}
