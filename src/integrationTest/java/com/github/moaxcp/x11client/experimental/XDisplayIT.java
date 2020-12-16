package com.github.moaxcp.x11client.experimental;

import com.github.moaxcp.x11client.X11Client;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.xproto.*;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class XDisplayIT {

  @Test
  void clientTestXFunctions() throws IOException {
    try(XDisplay client = new XDisplay(X11Client.connect())) {
      int wid = client.createSimpleWindow(0, (short) 10, (short) 10, (short) 600, (short) 480, EventMask.EXPOSURE, EventMask.KEY_PRESS);
      client.storeName(wid, "Hello World!");
      int deleteAtom = client.internAtom("WM_DELETE_WINDOW");
      client.setWMProtocols(wid, deleteAtom);
      client.mapWindow(wid);
      int gc = client.createGC(0, wid);
      while(true) {
        XEvent event = client.getNextEvent();
        if(event instanceof ExposeEvent) {
          client.fillRectangle(wid, gc, (short) 20, (short) 20, (short) 10, (short) 10);
          client.drawString(wid, gc, (short) 10, (short) 50, "Hello World!");
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
        } else {
          throw new IllegalStateException(event.toString());
        }
      }
    }
  }
}
