package com.github.moaxcp.x11client.protocol.dri2;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XError;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class Dri2Plugin implements XProtocolPlugin {
  public static final String NAME = "DRI2";

  @Getter
  private byte majorVersion = 1;

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
    if(request instanceof Connect) {
      return true;
    }
    if(request instanceof Authenticate) {
      return true;
    }
    if(request instanceof CreateDrawable) {
      return true;
    }
    if(request instanceof DestroyDrawable) {
      return true;
    }
    if(request instanceof GetBuffers) {
      return true;
    }
    if(request instanceof CopyRegion) {
      return true;
    }
    if(request instanceof GetBuffersWithFormat) {
      return true;
    }
    if(request instanceof SwapBuffers) {
      return true;
    }
    if(request instanceof GetMSC) {
      return true;
    }
    if(request instanceof WaitMSC) {
      return true;
    }
    if(request instanceof WaitSBC) {
      return true;
    }
    if(request instanceof SwapInterval) {
      return true;
    }
    if(request instanceof GetParam) {
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
    return false;
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    if(number - firstEvent == 0) {
      return BufferSwapCompleteEvent.readBufferSwapCompleteEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 1) {
      return InvalidateBuffersEvent.readInvalidateBuffersEvent(firstEvent, sentEvent, in);
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
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
