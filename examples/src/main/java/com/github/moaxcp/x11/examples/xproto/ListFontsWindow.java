package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.protocol.Utilities;
import com.github.moaxcp.x11.protocol.xproto.GetFontPath;
import com.github.moaxcp.x11.protocol.xproto.ListFonts;
import com.github.moaxcp.x11.protocol.xproto.ListFontsWithInfo;
import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;

import static com.github.moaxcp.x11.protocol.Utilities.*;

public class ListFontsWindow {
  public static void main(String... args) throws IOException {
    try (var client = X11Client.connect()) {
      var path = client.send(GetFontPath.builder().build());
      System.out.println("Paths:");
      path.getPath().forEach(str -> System.out.println(Utilities.toString(str.getName()).indent(4)));

      System.out.println("Fonts:");

      var listFontsRequest = ListFonts.builder()
        .maxNames(Short.MAX_VALUE)
        .pattern(toByteList("*"))
        .build();
      var fonts = client.send(listFontsRequest);

      fonts.getNames().forEach(str -> System.out.println(Utilities.toString(str.getName()).indent(4)));

      System.out.println("Fonts with info:");

      var listFontsWithInfoRequest = ListFontsWithInfo.builder()
          .maxNames(Short.MAX_VALUE)
          .pattern(toByteList("*"))
          .build();
      var fontsWithInfo = client.send(listFontsWithInfoRequest);

      while (!fontsWithInfo.getName().isEmpty()) {
        System.out.println("%s %d".formatted(Utilities.toString(fontsWithInfo.getName()), fontsWithInfo.getRepliesHint()).indent(4));
        fontsWithInfo = client.getNextReply(listFontsWithInfoRequest.getReplyFunction());
      }
    }
  }
}
