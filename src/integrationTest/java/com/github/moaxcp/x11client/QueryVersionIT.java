package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.record.QueryVersion;
import com.github.moaxcp.x11client.protocol.record.QueryVersionReply;
import java.io.IOException;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

@Log
public class QueryVersionIT {
  @Test
  void record() throws IOException {
    try (X11Client client = X11Client.connect()) {
      QueryVersion queryVersion = QueryVersion.builder().majorVersion((short) 1).minorVersion((short) 13).build();
      QueryVersionReply reply = client.send(queryVersion);
      log.info(reply.toString());
    }
  }
}
