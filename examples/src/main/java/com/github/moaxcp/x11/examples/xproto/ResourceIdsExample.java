package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;

public class ResourceIdsExample {
  public static void main(String... args) throws IOException {
    try (var client = X11Client.connect()) {
      while (true) {
        var id = client.nextResourceId();
        System.out.println(id);
        if (!(((id + 1) & ~client.getSetup().getResourceIdMask()) == 0)) {
          System.out.println("requesting more ids");
          return;
        }
      }
    }
  }
}
