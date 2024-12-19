package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.protocol.xproto.AllocColor;
import com.github.moaxcp.x11.protocol.xproto.ColormapAlloc;
import com.github.moaxcp.x11.protocol.xproto.CreateColormap;
import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;

public class CreateColorMapExample {
  public static void main(String... args) throws IOException, InterruptedException {
    try (var client = X11Client.connect()) {
      var wid = client.createSimpleWindow(0, 0, 150, 150);
      client.mapWindow(wid);
      var cmap = client.nextResourceId();
      client.send(CreateColormap.builder()
          .alloc(ColormapAlloc.NONE)
          .mid(cmap)
          .window(wid)
          .visual(client.getDefaultScreen().getRootVisual())
          .build());
      System.out.println(client.send(AllocColor.builder()
          .cmap(cmap)
          .red((short) 65535)
          .green((short) 60000)
          .blue((short) 30000)
          .build()));
    }
  }
}
