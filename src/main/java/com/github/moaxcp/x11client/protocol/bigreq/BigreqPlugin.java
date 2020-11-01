package com.github.moaxcp.x11client.protocol.bigreq;

import com.github.moaxcp.x11client.XProtocolService;
import com.github.moaxcp.x11client.protocol.*;
import com.github.moaxcp.x11client.protocol.xproto.QueryExtensionReply;
import com.github.moaxcp.x11client.protocol.xproto.QueryExtensionRequest;
import lombok.Getter;

import java.io.IOException;

public class BigreqPlugin implements XProtocolPlugin {
  @Getter
  private final String name = "BIG-REQUESTS";
  @Getter
  private byte offset;

  @Override
  public void setupOffset(XProtocolService service) throws IOException {
    QueryExtensionRequest bigRequests = QueryExtensionRequest.builder()
      .name(name)
      .build();
    service.send(bigRequests);
    QueryExtensionReply bigRequestReply = service.read();
    offset = bigRequestReply.getMajorOpcode();
  }

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
