package com.github.moaxcp.x11.examples.xfixes;

import com.github.moaxcp.x11.protocol.xfixes.QueryVersion;
import com.github.moaxcp.x11.protocol.xfixes.SelectSelectionInput;
import com.github.moaxcp.x11.protocol.xfixes.SelectionEventMask;
import com.github.moaxcp.x11.protocol.xfixes.SelectionNotifyEvent;
import com.github.moaxcp.x11.protocol.xproto.*;
import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;

import static com.github.moaxcp.x11.protocol.Utilities.toByteList;

public class XFixesSelection {
  public static void main(String... args) throws IOException {
    try (var client = X11Client.connect()) {
      var version = client.send(QueryVersion.builder().build());
      System.out.println(version);
      var extension = client.send(QueryExtension.builder()
          .name(toByteList("XFIXES"))
          .build());
      System.out.println(extension);
      var clipboard = client.getAtom("CLIPBOARD");
      System.out.println(clipboard);
      var primary = client.getAtom(Atom.PRIMARY.toString());
      System.out.println(primary);
      client.send(SelectSelectionInput.builder()
        .window(client.getDefaultRoot())
        .selection(clipboard.getId())
        .eventMaskEnable(SelectionEventMask.SET_SELECTION_OWNER)
        .build());
      client.sync();
      client.send(SelectSelectionInput.builder()
          .window(client.getDefaultRoot())
          .selection(primary.getId())
          .eventMaskEnable(SelectionEventMask.SET_SELECTION_OWNER)
          .build());
      while(true) {
        var event = client.getNextEvent();
        if (event instanceof SelectionNotifyEvent selection) {
          System.out.println(selection);
        }
      }
    }
  }
}
