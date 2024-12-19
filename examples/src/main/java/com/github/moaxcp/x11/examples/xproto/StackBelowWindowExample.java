package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.protocol.xproto.ConfigureWindow;
import com.github.moaxcp.x11.protocol.xproto.StackMode;
import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class StackBelowWindowExample {
  public static void main(String... args) throws IOException, InterruptedException {
    try (var client = X11Client.connect()) {
      var wid = client.createSimpleWindow(0, 0, 150, 150);
      client.mapWindow(wid);
      client.sync();
      sleep(10_000);
      client.send(ConfigureWindow.builder()
          .window(wid)
          .stackMode(StackMode.BELOW)
          .build());
      client.sync();
      sleep(10_000);
    }
  }
}
