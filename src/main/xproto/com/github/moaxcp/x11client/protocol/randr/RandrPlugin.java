package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XError;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class RandrPlugin implements XProtocolPlugin {
  public static final String NAME = "RANDR";

  @Getter
  private byte majorVersion = 1;

  @Getter
  private byte minorVersion = 6;

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
    if(request instanceof SetScreenConfig) {
      return true;
    }
    if(request instanceof SelectInput) {
      return true;
    }
    if(request instanceof GetScreenInfo) {
      return true;
    }
    if(request instanceof GetScreenSizeRange) {
      return true;
    }
    if(request instanceof SetScreenSize) {
      return true;
    }
    if(request instanceof GetScreenResources) {
      return true;
    }
    if(request instanceof GetOutputInfo) {
      return true;
    }
    if(request instanceof ListOutputProperties) {
      return true;
    }
    if(request instanceof QueryOutputProperty) {
      return true;
    }
    if(request instanceof ConfigureOutputProperty) {
      return true;
    }
    if(request instanceof ChangeOutputProperty) {
      return true;
    }
    if(request instanceof DeleteOutputProperty) {
      return true;
    }
    if(request instanceof GetOutputProperty) {
      return true;
    }
    if(request instanceof CreateMode) {
      return true;
    }
    if(request instanceof DestroyMode) {
      return true;
    }
    if(request instanceof AddOutputMode) {
      return true;
    }
    if(request instanceof DeleteOutputMode) {
      return true;
    }
    if(request instanceof GetCrtcInfo) {
      return true;
    }
    if(request instanceof SetCrtcConfig) {
      return true;
    }
    if(request instanceof GetCrtcGammaSize) {
      return true;
    }
    if(request instanceof GetCrtcGamma) {
      return true;
    }
    if(request instanceof SetCrtcGamma) {
      return true;
    }
    if(request instanceof GetScreenResourcesCurrent) {
      return true;
    }
    if(request instanceof SetCrtcTransform) {
      return true;
    }
    if(request instanceof GetCrtcTransform) {
      return true;
    }
    if(request instanceof GetPanning) {
      return true;
    }
    if(request instanceof SetPanning) {
      return true;
    }
    if(request instanceof SetOutputPrimary) {
      return true;
    }
    if(request instanceof GetOutputPrimary) {
      return true;
    }
    if(request instanceof GetProviders) {
      return true;
    }
    if(request instanceof GetProviderInfo) {
      return true;
    }
    if(request instanceof SetProviderOffloadSink) {
      return true;
    }
    if(request instanceof SetProviderOutputSource) {
      return true;
    }
    if(request instanceof ListProviderProperties) {
      return true;
    }
    if(request instanceof QueryProviderProperty) {
      return true;
    }
    if(request instanceof ConfigureProviderProperty) {
      return true;
    }
    if(request instanceof ChangeProviderProperty) {
      return true;
    }
    if(request instanceof DeleteProviderProperty) {
      return true;
    }
    if(request instanceof GetProviderProperty) {
      return true;
    }
    if(request instanceof GetMonitors) {
      return true;
    }
    if(request instanceof SetMonitor) {
      return true;
    }
    if(request instanceof DeleteMonitor) {
      return true;
    }
    if(request instanceof CreateLease) {
      return true;
    }
    if(request instanceof FreeLease) {
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
    if(code - firstError == 3) {
      return true;
    }
    return false;
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    if(number - firstEvent == 0) {
      return ScreenChangeNotifyEvent.readScreenChangeNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 1) {
      return NotifyEvent.readNotifyEvent(firstEvent, sentEvent, in);
    }
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    if(code - firstError == 0) {
      return BadOutputError.readBadOutputError(firstError, in);
    }
    if(code - firstError == 1) {
      return BadCrtcError.readBadCrtcError(firstError, in);
    }
    if(code - firstError == 2) {
      return BadModeError.readBadModeError(firstError, in);
    }
    if(code - firstError == 3) {
      return BadProviderError.readBadProviderError(firstError, in);
    }
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(boolean sentEvent, byte extension, short sequenceNumber,
      int length, short eventType, X11Input in) throws IOException {
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
