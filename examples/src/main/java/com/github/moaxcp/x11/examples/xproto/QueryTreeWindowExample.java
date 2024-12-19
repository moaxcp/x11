package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.protocol.xproto.GetGeometry;
import com.github.moaxcp.x11.protocol.xproto.QueryTree;
import com.github.moaxcp.x11.protocol.xproto.TranslateCoordinates;
import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class QueryTreeWindowExample {
  public static void main(String... args) throws IOException, InterruptedException {
    try (var client = X11Client.connect()) {
      var wid = client.createSimpleWindow(0, 0, 150, 150);
      client.mapWindow(wid);
      var geometry = client.send(GetGeometry.builder()
          .drawable(wid)
          .build());
      var query = client.send(QueryTree.builder()
          .window(wid)
          .build());
      System.out.println(client.send(TranslateCoordinates.builder()
          .srcWindow(wid)
          .dstWindow(query.getParent())
          .srcX(geometry.getX())
          .srcY(geometry.getY())
          .build()));
      sleep(10_000);
    }
  }
}
