package com.github.moaxcp.x11.examples.xproto;


import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.xproto.*;
import com.github.moaxcp.x11.x11client.X11Client;

import java.awt.*;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Random;

import static com.github.moaxcp.x11.protocol.xproto.EventMask.*;

public class DisplayEventsExample {

  public static short WIDTH = (short) 1200;
  public static short HEIGHT = (short) 1000;

  public static void main(String... args) {
    var random = new Random();
    try(X11Client client = X11Client.connect()) {
      int wid = client.createSimpleWindow((short) 10, (short) 10, WIDTH, HEIGHT,
        EXPOSURE, NO_EVENT, KEY_PRESS, KEY_RELEASE, BUTTON_PRESS, BUTTON_RELEASE,ENTER_WINDOW, LEAVE_WINDOW,
        POINTER_MOTION, POINTER_MOTION_HINT, BUTTON1_MOTION, BUTTON2_MOTION, BUTTON3_MOTION, BUTTON4_MOTION,
        BUTTON5_MOTION, BUTTON_MOTION, KEYMAP_STATE, VISIBILITY_CHANGE, STRUCTURE_NOTIFY, RESIZE_REDIRECT,
        SUBSTRUCTURE_NOTIFY, SUBSTRUCTURE_REDIRECT, FOCUS_CHANGE, PROPERTY_CHANGE, COLOR_MAP_CHANGE, OWNER_GRAB_BUTTON);
      client.setWindowName(wid, "Expose Window");
      int deleteAtom = client.getAtom("WM_DELETE_WINDOW").getId();
      client.setWMProtocols(wid, deleteAtom);
      client.mapWindow(wid);
      int black = client.nextResourceId();
      client.send(CreateGC.builder()
        .cid(black)
        .drawable(wid)
        .fillStyle(FillStyle.TILED)
        .lineStyle(LineStyle.SOLID)
        .lineWidth(1)
        .capStyle(CapStyle.BUTT)
        .joinStyle(JoinStyle.MITER)
        .background(new Color(random.nextInt(128, 255), random.nextInt(120, 255), random.nextInt(128, 255)).getRGB())
        .foreground(client.getBlackPixel(0))
        .build());
      int fill = client.nextResourceId();
      client.send(CreateGC.builder()
        .cid(fill)
        .drawable(wid)
        .fillStyle(FillStyle.TILED)
        .lineStyle(LineStyle.SOLID)
        .lineWidth(10)
        .capStyle(CapStyle.BUTT)
        .joinStyle(JoinStyle.MITER)
        .foreground(new Color(random.nextInt(128, 255), random.nextInt(120, 255), random.nextInt(128, 255)).getRGB())
        .background(client.getBlackPixel(0))
        .build());
      var eventString = "";
      var width = WIDTH;
      var height = HEIGHT;
      while(true) {
        XEvent event = client.getNextEvent();
        System.out.println(event);
        if(event instanceof ExposeEvent exposeEvent) {
          client.fillRectangle(wid, fill, (short) 0, (short) 0, width, height);
          client.imageText8(wid, black, (short) 0, (short) 10, eventString);
          client.imageText8(wid, black, (short) 0, (short) 30, event.toString());
        } else if(event instanceof ResizeRequestEvent resize) {
          var geometry = client.send(GetGeometry.builder()
            .drawable(wid)
            .build());
          width = geometry.getWidth();
          height = geometry.getHeight();
        }else if(event instanceof ClientMessageEvent) {
          eventString = event.toString();
          client.exposeWindow(wid);
          ClientMessageEvent clientMessage = (ClientMessageEvent) event;
          if(clientMessage.getFormat() == 32) {
            ClientMessageData32 data = (ClientMessageData32) clientMessage.getData();
            if(data.getData32().get(0) == deleteAtom) {
              break;
            }
          }
        } else {
          eventString = event.toString();
          client.exposeWindow(wid);
        }
      }
    } catch (IOException e) {
        throw new UncheckedIOException(e);
    }
  }
}
