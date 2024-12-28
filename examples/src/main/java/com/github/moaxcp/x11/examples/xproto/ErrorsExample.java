package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.protocol.X11ErrorException;
import com.github.moaxcp.x11.protocol.xproto.CreateColormap;
import com.github.moaxcp.x11.protocol.xproto.GetGeometry;
import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;

public class ErrorsExample {
  public static void main(String... args) throws IOException {
    valueError();
    drawableError();
  }

  private static void valueError() throws IOException {
    try (var client = X11Client.connect()) {
      var cmap = client.nextResourceId();
      client.send(CreateColormap.builder()
          .alloc((byte) 111)
          .mid(cmap)
          .window(client.getDefaultRoot())
          .visual(client.getDefaultScreen().getRootVisual())
          .build());
      client.sync();
    } catch (X11ErrorException e) {
      System.out.println(e);
    }
  }

  private static void drawableError() throws IOException {
    try (var client = X11Client.connect()) {
      var invalidWindow = client.nextResourceId();
      var getGeometry = GetGeometry.builder().drawable(invalidWindow).build();
      System.out.println(getGeometry);
      client.send(getGeometry);
    } catch (X11ErrorException e) {
      System.out.println(e);
    }
  }
}
