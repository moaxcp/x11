package com.github.moaxcp.x11.examples.xproto;


import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.xproto.*;
import com.github.moaxcp.x11.x11client.X11Client;

import java.awt.*;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Random;

public class ChineseFontExample {

  public static void main(String... args) {
    var random = new Random();
    try(X11Client client = X11Client.connect()) {
      int wid = client.createSimpleWindow((short) 10, (short) 10, 1200, 1000, EventMask.EXPOSURE);
      client.setWindowName(wid, "Expose Window");
      client.setIconName(wid, "Expose Window (Icon)");
      int deleteAtom = client.getAtom("WM_DELETE_WINDOW").getId();
      client.setWMProtocols(wid, deleteAtom);
      client.mapWindow(wid);
      var fonts = client.listFonts("*big5*");
      var font = client.openFont(fonts.getFirst());
      int lineGc = client.nextResourceId();
      client.send(CreateGC.builder()
        .cid(lineGc)
        .drawable(wid)
        .font(font)
        .fillStyle(FillStyle.TILED)
        .lineStyle(LineStyle.SOLID)
        .lineWidth(1)
        .capStyle(CapStyle.BUTT)
        .joinStyle(JoinStyle.MITER)
        .background(new Color(random.nextInt(128, 255), random.nextInt(120, 255), random.nextInt(128, 255)).getRGB())
        .foreground(client.getBlackPixel(0))
        .build());
      int fillGc = client.nextResourceId();
      client.send(CreateGC.builder()
        .cid(fillGc)
        .drawable(wid)
        .fillStyle(FillStyle.TILED)
        .lineStyle(LineStyle.SOLID)
        .lineWidth(10)
        .capStyle(CapStyle.BUTT)
        .joinStyle(JoinStyle.MITER)
        .foreground(new Color(random.nextInt(128, 255), random.nextInt(120, 255), random.nextInt(128, 255)).getRGB())
        .background(client.getBlackPixel(0))
        .build());
      while(true) {
        XEvent event = client.getNextEvent();
        if(event instanceof ExposeEvent) {
          client.imageText8(wid, lineGc, (short) 10, (short) 10, "\u00a7A\u00a6n\u00b6\u00dc");
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
    } catch (IOException e) {
        throw new UncheckedIOException(e);
    }
  }
}
