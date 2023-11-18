package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XError;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class XprintPlugin implements XProtocolPlugin {
  public static final String NAME = "XpExtension";

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
    if(request instanceof PrintQueryVersion) {
      return true;
    }
    if(request instanceof PrintGetPrinterList) {
      return true;
    }
    if(request instanceof PrintRehashPrinterList) {
      return true;
    }
    if(request instanceof CreateContext) {
      return true;
    }
    if(request instanceof PrintSetContext) {
      return true;
    }
    if(request instanceof PrintGetContext) {
      return true;
    }
    if(request instanceof PrintDestroyContext) {
      return true;
    }
    if(request instanceof PrintGetScreenOfContext) {
      return true;
    }
    if(request instanceof PrintStartJob) {
      return true;
    }
    if(request instanceof PrintEndJob) {
      return true;
    }
    if(request instanceof PrintStartDoc) {
      return true;
    }
    if(request instanceof PrintEndDoc) {
      return true;
    }
    if(request instanceof PrintPutDocumentData) {
      return true;
    }
    if(request instanceof PrintGetDocumentData) {
      return true;
    }
    if(request instanceof PrintStartPage) {
      return true;
    }
    if(request instanceof PrintEndPage) {
      return true;
    }
    if(request instanceof PrintSelectInput) {
      return true;
    }
    if(request instanceof PrintInputSelected) {
      return true;
    }
    if(request instanceof PrintGetAttributes) {
      return true;
    }
    if(request instanceof PrintGetOneAttributes) {
      return true;
    }
    if(request instanceof PrintSetAttributes) {
      return true;
    }
    if(request instanceof PrintGetPageDimensions) {
      return true;
    }
    if(request instanceof PrintQueryScreens) {
      return true;
    }
    if(request instanceof PrintSetImageResolution) {
      return true;
    }
    if(request instanceof PrintGetImageResolution) {
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
    return false;
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    if(number - firstEvent == 0) {
      return NotifyEvent.readNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 1) {
      return AttributNotifyEvent.readAttributNotifyEvent(firstEvent, sentEvent, in);
    }
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    if(code - firstError == 0) {
      return BadContextError.readBadContextError(firstError, in);
    }
    if(code - firstError == 1) {
      return BadSequenceError.readBadSequenceError(firstError, in);
    }
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(boolean sentEvent, byte extension, short sequenceNumber,
      int length, short eventType, X11Input in) throws IOException {
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
