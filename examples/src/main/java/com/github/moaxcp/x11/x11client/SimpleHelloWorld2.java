package com.github.moaxcp.x11.x11client;


import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.xproto.*;

import java.io.IOException;

public class SimpleHelloWorld2 {

  public static void main(String... args) throws IOException {
    try(X11Client client = X11Client.connect()) {
      int wid = client.createSimpleWindow((short) 10, (short) 10, (short) 600, (short) 480, EventMask.EXPOSURE, EventMask.KEY_PRESS);
      client.storeName(wid, "Hello World!");
      int deleteAtom = client.getAtom("WM_DELETE_WINDOW").getId();
      client.setWMProtocols(wid, deleteAtom);
      client.mapWindow(wid);
      int gc = client.createGC(0, wid);
      while(true) {
        XEvent event = client.getNextEvent();
        if(event instanceof ExposeEvent) {
          client.fillRectangle(wid, gc, (short) 20, (short) 20, (short) 10, (short) 10);
          client.imageText8(wid, gc, (short) 10, (short) 50, "Hello World!");
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
    }
  }
}
