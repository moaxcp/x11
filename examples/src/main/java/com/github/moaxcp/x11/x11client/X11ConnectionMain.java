package com.github.moaxcp.x11.x11client;

import com.github.moaxcp.x11.protocol.DisplayName;
import com.github.moaxcp.x11.protocol.Utilities;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.QueryExtension;
import com.github.moaxcp.x11.protocol.xproto.QueryExtensionReply;
import com.github.moaxcp.x11.xephyr.XephyrRunner;

import java.io.IOException;

public class X11ConnectionMain {
  public static void main(String... args) throws IOException, InterruptedException {
    XephyrRunner runner = XephyrRunner.builder()
            .ac(true)
            .br(true)
            .noreset(true)
            .build();
    runner.start();
    try(X11Connection connection = X11Connection.connect(new DisplayName(runner.getDisplay()))) {
      X11Output out = connection.getX11Output();
      X11Input in = connection.getX11Input();
      QueryExtension extension = QueryExtension.builder()
        .name(Utilities.toByteList("XC-MISC"))
        .build();
      extension.write((byte) 0, out);
      byte status = in.readByte();
      if(status == 0) {
        //error
      } else if(status == 1) {
        //success
        byte field = in.readByte();
        short sequenceNumber = in.readCard16();
        QueryExtensionReply reply = QueryExtensionReply.readQueryExtensionReply(field, sequenceNumber, in);
        System.out.println(reply);
      } else {
        //event
      }
    }
    runner.stop();
  }
}
