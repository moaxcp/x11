package com.github.moaxcp.x11client.protocol.xselinux;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XError;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class XselinuxPlugin implements XProtocolPlugin {
  public static final String NAME = "SELinux";

  @Getter
  private byte majorVersion = 1;

  @Getter
  private byte minorVersion = 0;

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
    if(request instanceof SetDeviceCreateContext) {
      return true;
    }
    if(request instanceof GetDeviceCreateContext) {
      return true;
    }
    if(request instanceof SetDeviceContext) {
      return true;
    }
    if(request instanceof GetDeviceContext) {
      return true;
    }
    if(request instanceof SetWindowCreateContext) {
      return true;
    }
    if(request instanceof GetWindowCreateContext) {
      return true;
    }
    if(request instanceof GetWindowContext) {
      return true;
    }
    if(request instanceof SetPropertyCreateContext) {
      return true;
    }
    if(request instanceof GetPropertyCreateContext) {
      return true;
    }
    if(request instanceof SetPropertyUseContext) {
      return true;
    }
    if(request instanceof GetPropertyUseContext) {
      return true;
    }
    if(request instanceof GetPropertyContext) {
      return true;
    }
    if(request instanceof GetPropertyDataContext) {
      return true;
    }
    if(request instanceof ListProperties) {
      return true;
    }
    if(request instanceof SetSelectionCreateContext) {
      return true;
    }
    if(request instanceof GetSelectionCreateContext) {
      return true;
    }
    if(request instanceof SetSelectionUseContext) {
      return true;
    }
    if(request instanceof GetSelectionUseContext) {
      return true;
    }
    if(request instanceof GetSelectionContext) {
      return true;
    }
    if(request instanceof GetSelectionDataContext) {
      return true;
    }
    if(request instanceof ListSelections) {
      return true;
    }
    if(request instanceof GetClientContext) {
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
