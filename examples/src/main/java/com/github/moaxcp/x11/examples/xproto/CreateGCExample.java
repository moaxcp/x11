package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;

public class CreateGCExample {
  public static void main(String... args) throws IOException, InterruptedException {
    try (var client = X11Client.connect()) {
      var wid = client.createSimpleWindow(0, 0, 150, 150);
      var gc = client.createGC(client.getDefaultScreenNumber(), wid);
      client.sync();
    }
  }
}
