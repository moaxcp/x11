package com.github.moaxcp.x11client.protocol.xc_misc;

import com.github.moaxcp.x11client.protocol.*;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class XCMISCPlugin implements XProtocolPlugin {
  @Getter
  private final String name = "XC-MISC";
  @Getter
  @Setter
  private byte offset;

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
