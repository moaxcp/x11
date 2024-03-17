package com.github.moaxcp.x11.examples;

import com.github.moaxcp.x11.protocol.record.QueryVersionReply;
import com.github.moaxcp.x11.x11client.X11Client;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
public class QueryVersion {

  public static void main(String... args) throws IOException {
    try (X11Client client = X11Client.connect()) {
      com.github.moaxcp.x11.protocol.record.QueryVersion queryVersion = com.github.moaxcp.x11.protocol.record.QueryVersion.builder().majorVersion((short) 1).minorVersion((short) 13).build();
      QueryVersionReply reply = client.send(queryVersion);
      log.info(reply.toString());
    }
  }
}
