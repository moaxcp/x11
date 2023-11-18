package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XError;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class XvPlugin implements XProtocolPlugin {
  public static final String NAME = "XVideo";

  @Getter
  private byte majorVersion = 2;

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
    if(request instanceof QueryExtension) {
      return true;
    }
    if(request instanceof QueryAdaptors) {
      return true;
    }
    if(request instanceof QueryEncodings) {
      return true;
    }
    if(request instanceof GrabPort) {
      return true;
    }
    if(request instanceof UngrabPort) {
      return true;
    }
    if(request instanceof PutVideo) {
      return true;
    }
    if(request instanceof PutStill) {
      return true;
    }
    if(request instanceof GetVideo) {
      return true;
    }
    if(request instanceof GetStill) {
      return true;
    }
    if(request instanceof StopVideo) {
      return true;
    }
    if(request instanceof SelectVideoNotify) {
      return true;
    }
    if(request instanceof SelectPortNotify) {
      return true;
    }
    if(request instanceof QueryBestSize) {
      return true;
    }
    if(request instanceof SetPortAttribute) {
      return true;
    }
    if(request instanceof GetPortAttribute) {
      return true;
    }
    if(request instanceof QueryPortAttributes) {
      return true;
    }
    if(request instanceof ListImageFormats) {
      return true;
    }
    if(request instanceof QueryImageAttributes) {
      return true;
    }
    if(request instanceof PutImage) {
      return true;
    }
    if(request instanceof ShmPutImage) {
      return true;
    }
    return false;
  }

  @Override
  public boolean supportedEvent(byte number) {
    if(number - firstEvent == 0) {
      return true;
    }
    if(number - firstEvent == 1) {
      return true;
    }
    return false;
  }

  @Override
  public boolean supportedError(byte code) {
    if(code - firstError == 0) {
      return true;
    }
    if(code - firstError == 1) {
      return true;
    }
    if(code - firstError == 2) {
      return true;
    }
    return false;
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    if(number - firstEvent == 0) {
      return VideoNotifyEvent.readVideoNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 1) {
      return PortNotifyEvent.readPortNotifyEvent(firstEvent, sentEvent, in);
    }
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    if(code - firstError == 0) {
      return BadPortError.readBadPortError(firstError, in);
    }
    if(code - firstError == 1) {
      return BadEncodingError.readBadEncodingError(firstError, in);
    }
    if(code - firstError == 2) {
      return BadControlError.readBadControlError(firstError, in);
    }
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(boolean sentEvent, byte extension, short sequenceNumber,
      int length, short eventType, X11Input in) throws IOException {
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
