package com.github.moaxcp.x11client.protocol.xc_misc;

import com.github.moaxcp.x11client.XProtocolService;
import com.github.moaxcp.x11client.protocol.*;
import com.github.moaxcp.x11client.protocol.xproto.QueryExtensionReply;
import com.github.moaxcp.x11client.protocol.xproto.QueryExtensionRequest;
import lombok.Getter;

import java.io.IOException;

public class XCMISCPlugin implements XProtocolPlugin {

  @Getter
  private final String name = "XC-MISC";
  @Getter
  private byte offset;

  @Override
  public void setupOffset(XProtocolService service) throws IOException {
    QueryExtensionRequest request = new QueryExtensionRequest();
    request.setName(name);
    service.send(request);
    QueryExtensionReply reply = service.read();
    offset = reply.getMajorOpcode();
  }

  @Override
  public boolean supportedRequest(XRequest request) {
    if(request instanceof GetVersionRequest) {
      return true;
    }
    if(request instanceof GetXIDListRequest) {
      return true;
    }
    if(request instanceof GetXIDRangeRequest) {
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
    return null;
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    return null;
  }
}
