package com.github.moaxcp.x11client.protocol.xf86vidmode;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XError;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class Xf86vidmodePlugin implements XProtocolPlugin {
  public static final String NAME = "XFree86-VidModeExtension";

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
    if(request instanceof QueryVersion) {
      return true;
    }
    if(request instanceof GetModeLine) {
      return true;
    }
    if(request instanceof ModModeLine) {
      return true;
    }
    if(request instanceof SwitchMode) {
      return true;
    }
    if(request instanceof GetMonitor) {
      return true;
    }
    if(request instanceof LockModeSwitch) {
      return true;
    }
    if(request instanceof GetAllModeLines) {
      return true;
    }
    if(request instanceof AddModeLine) {
      return true;
    }
    if(request instanceof DeleteModeLine) {
      return true;
    }
    if(request instanceof ValidateModeLine) {
      return true;
    }
    if(request instanceof SwitchToMode) {
      return true;
    }
    if(request instanceof GetViewPort) {
      return true;
    }
    if(request instanceof SetViewPort) {
      return true;
    }
    if(request instanceof GetDotClocks) {
      return true;
    }
    if(request instanceof SetClientVersion) {
      return true;
    }
    if(request instanceof SetGamma) {
      return true;
    }
    if(request instanceof GetGamma) {
      return true;
    }
    if(request instanceof GetGammaRamp) {
      return true;
    }
    if(request instanceof SetGammaRamp) {
      return true;
    }
    if(request instanceof GetGammaRampSize) {
      return true;
    }
    if(request instanceof GetPermissions) {
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
    if(code - firstError == 4) {
      return true;
    }
    if(code - firstError == 5) {
      return true;
    }
    if(code - firstError == 6) {
      return true;
    }
    return false;
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    if(code - firstError == 0) {
      return BadClockError.readBadClockError(firstError, in);
    }
    if(code - firstError == 1) {
      return BadHTimingsError.readBadHTimingsError(firstError, in);
    }
    if(code - firstError == 2) {
      return BadVTimingsError.readBadVTimingsError(firstError, in);
    }
    if(code - firstError == 3) {
      return ModeUnsuitableError.readModeUnsuitableError(firstError, in);
    }
    if(code - firstError == 4) {
      return ExtensionDisabledError.readExtensionDisabledError(firstError, in);
    }
    if(code - firstError == 5) {
      return ClientNotLocalError.readClientNotLocalError(firstError, in);
    }
    if(code - firstError == 6) {
      return ZoomLockedError.readZoomLockedError(firstError, in);
    }
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(boolean sentEvent, byte extension, short sequenceNumber,
      int length, short eventType, X11Input in) throws IOException {
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
