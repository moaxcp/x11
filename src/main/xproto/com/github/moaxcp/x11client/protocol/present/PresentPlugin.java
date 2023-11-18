package com.github.moaxcp.x11client.protocol.present;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XError;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class PresentPlugin implements XProtocolPlugin {
  public static final String NAME = "Present";

  @Getter
  private byte majorVersion = 1;

  @Getter
  private byte minorVersion = 2;

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
    if(request instanceof Pixmap) {
      return true;
    }
    if(request instanceof NotifyMSC) {
      return true;
    }
    if(request instanceof SelectInput) {
      return true;
    }
    if(request instanceof QueryCapabilities) {
      return true;
    }
    return false;
  }

  @Override
  public boolean supportedEvent(byte number) {
    if(number - firstEvent == 0) {
      return true;
    }
    return false;
  }

  @Override
  public boolean supportedError(byte code) {
    return false;
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    if(number - firstEvent == 0) {
      return GenericEvent.readGenericEvent(firstEvent, sentEvent, in);
    }
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(boolean sentEvent, byte extension, short sequenceNumber,
      int length, short eventType, X11Input in) throws IOException {
    if(eventType == 0) {
    }
    if(eventType == 1) {
    }
    if(eventType == 2) {
    }
    if(eventType == 3) {
    }
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
