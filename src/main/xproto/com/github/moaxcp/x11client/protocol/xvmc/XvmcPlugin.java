package com.github.moaxcp.x11client.protocol.xvmc;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XError;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class XvmcPlugin implements XProtocolPlugin {
  public static final String NAME = "XVideo-MotionCompensation";

  @Getter
  private byte majorVersion = 1;

  @Getter
  private byte minorVersion = 1;

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
    if(request instanceof ListSurfaceTypes) {
      return true;
    }
    if(request instanceof CreateContext) {
      return true;
    }
    if(request instanceof DestroyContext) {
      return true;
    }
    if(request instanceof CreateSurface) {
      return true;
    }
    if(request instanceof DestroySurface) {
      return true;
    }
    if(request instanceof CreateSubpicture) {
      return true;
    }
    if(request instanceof DestroySubpicture) {
      return true;
    }
    if(request instanceof ListSubpictureTypes) {
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
