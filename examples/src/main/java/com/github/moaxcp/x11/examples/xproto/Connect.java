package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;

public class Connect {
  public static void main(String... args) throws IOException {
    try (var client = X11Client.connect()) {
      client.sync();
    }
  }
}
