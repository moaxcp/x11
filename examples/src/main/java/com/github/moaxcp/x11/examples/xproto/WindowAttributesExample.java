package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.protocol.xproto.GetWindowAttributes;
import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class WindowAttributesExample {
  public static void main(String... args) throws IOException, InterruptedException {
    try (var client = X11Client.connect()) {
      var wid = client.createSimpleWindow(0, 0, 150, 150);
      client.mapWindow(wid);
      System.out.println(client.send(GetWindowAttributes.builder()
          .window(wid)
          .build()));
      sleep(10_000);
    }
  }
}
