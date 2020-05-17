package com.github.moaxcp.x11client;

import java.io.IOException;
import lombok.Value;

@Value
public class ConnectionFailure {
  int protocolMajorVersion;
  int protocolMinorVersion;
  String reason;

  public static ConnectionFailure readFailure(X11Input in) throws IOException {
    int reasonLength = in.readInt8();
    int majorVersion = in.readCard16();
    int minorVersion = in.readCard16();
    int additionalLength = in.readCard16();
    String reason = in.readString8(reasonLength);
    in.readPad(reason.length());
    return new ConnectionFailure(majorVersion, minorVersion, reason);
  }
}
