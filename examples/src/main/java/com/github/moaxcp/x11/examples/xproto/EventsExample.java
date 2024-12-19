package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;

import static com.github.moaxcp.x11.protocol.xproto.EventMask.BUTTON_PRESS;
import static com.github.moaxcp.x11.protocol.xproto.EventMask.EXPOSURE;

public class EventsExample {
  public static void main(String... args) throws IOException {
    try (var client = X11Client.connect()) {
      var wid = client.createSimpleWindow(0, 0, 150, 150, EXPOSURE, BUTTON_PRESS);
      client.sync();
    }
  }
}
