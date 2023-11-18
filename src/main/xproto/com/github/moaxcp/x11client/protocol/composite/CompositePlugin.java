package com.github.moaxcp.x11client.protocol.composite;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XError;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class CompositePlugin implements XProtocolPlugin {
  public static final String NAME = "Composite";

  @Getter
  private byte majorVersion = 0;

  @Getter
  private byte minorVersion = 4;

  @Getter
  @Setter
  private byte firstEvent;

  @Getter
  @Setter
  private byte firstError;

  public String getName() {
    return NAME;
  }

  @Override
  public boolean supportedRequest(XRequest request) {
    if(request instanceof QueryVersion) {
      return true;
    }
    if(request instanceof RedirectWindow) {
      return true;
    }
    if(request instanceof RedirectSubwindows) {
      return true;
    }
    if(request instanceof UnredirectWindow) {
      return true;
    }
    if(request instanceof UnredirectSubwindows) {
      return true;
    }
    if(request instanceof CreateRegionFromBorderClip) {
      return true;
    }
    if(request instanceof NameWindowPixmap) {
      return true;
    }
    if(request instanceof GetOverlayWindow) {
      return true;
    }
    if(request instanceof ReleaseOverlayWindow) {
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
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(boolean sentEvent, byte extension, short sequenceNumber,
      int length, short eventType, X11Input in) throws IOException {
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
