package com.github.moaxcp.x11.examples;

import com.github.moaxcp.x11.protocol.present.EventMask;
import com.github.moaxcp.x11.protocol.present.SelectInput;
import com.github.moaxcp.x11.protocol.xproto.ConfigureWindow;
import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;

public class GenericEventsExample {
  public static void main(String... args) throws IOException, InterruptedException {
    try (var client = X11Client.connect()) {
      var wid = client.createSimpleWindow(0, 0, 150, 150);
      client.mapWindow(wid);
      var eventId = client.nextResourceId();
      client.send(SelectInput.builder()
          .eid(eventId)
          .window(wid)
          .eventMaskEnable(EventMask.CONFIGURE_NOTIFY)
          .build());

      client.send(ConfigureWindow.builder()
          .window(wid)
          .x(500)
          .y(500)
          .build());

      System.out.println(client.getNextEvent());
    }
  }
}
