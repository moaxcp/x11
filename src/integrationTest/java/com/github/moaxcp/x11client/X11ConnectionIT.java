package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.DisplayName;
import com.github.moaxcp.x11client.protocol.Utilities;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.xproto.QueryExtension;
import com.github.moaxcp.x11client.protocol.xproto.QueryExtensionReply;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class X11ConnectionIT {
  private XephyrRunner runner;

  @BeforeEach
  void setup() throws IOException {
    runner = XephyrRunner.builder()
      .ac(true)
      .br(true)
      .noreset(true)
      .build();
    runner.start();
  }

  @AfterEach
  void teardown() throws InterruptedException {
    runner.stop();
  }

  @Test
  void test() throws IOException {
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
  }
}
