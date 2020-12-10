package com.github.moaxcp.x11client.protocol.bigreq;

import com.github.moaxcp.x11client.protocol.*;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class BigreqPlugin implements XProtocolPlugin {
  @Getter
  private final String name = "BIG-REQUESTS";
  @Getter
  @Setter
  private byte offset;

  @Override
  public boolean supportedRequest(XRequest request) {
    if(request instanceof EnableRequest) {
      return true;
    }
    return false;
  }

  @Override
  public boolean supportedEvent(byte number) {
    return false;
  }

  @Override
  public boolean supportedError(byte code) {
    return false;
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    throw new UnsupportedOperationException(name + " has no events");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    throw new UnsupportedOperationException(name + " has no errors");
  }
}
