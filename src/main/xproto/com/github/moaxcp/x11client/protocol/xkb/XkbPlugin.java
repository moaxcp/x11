package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XError;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class XkbPlugin implements XProtocolPlugin {
  public static final String NAME = "XKEYBOARD";

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
    if(request instanceof UseExtension) {
      return true;
    }
    if(request instanceof SelectEvents) {
      return true;
    }
    if(request instanceof Bell) {
      return true;
    }
    if(request instanceof GetState) {
      return true;
    }
    if(request instanceof LatchLockState) {
      return true;
    }
    if(request instanceof GetControls) {
      return true;
    }
    if(request instanceof SetControls) {
      return true;
    }
    if(request instanceof GetMap) {
      return true;
    }
    if(request instanceof SetMap) {
      return true;
    }
    if(request instanceof GetCompatMap) {
      return true;
    }
    if(request instanceof SetCompatMap) {
      return true;
    }
    if(request instanceof GetIndicatorState) {
      return true;
    }
    if(request instanceof GetIndicatorMap) {
      return true;
    }
    if(request instanceof SetIndicatorMap) {
      return true;
    }
    if(request instanceof GetNamedIndicator) {
      return true;
    }
    if(request instanceof SetNamedIndicator) {
      return true;
    }
    if(request instanceof GetNames) {
      return true;
    }
    if(request instanceof SetNames) {
      return true;
    }
    if(request instanceof PerClientFlags) {
      return true;
    }
    if(request instanceof ListComponents) {
      return true;
    }
    if(request instanceof GetDeviceInfo) {
      return true;
    }
    if(request instanceof SetDeviceInfo) {
      return true;
    }
    if(request instanceof SetDebuggingFlags) {
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
    if(number - firstEvent == 2) {
      return true;
    }
    if(number - firstEvent == 3) {
      return true;
    }
    if(number - firstEvent == 4) {
      return true;
    }
    if(number - firstEvent == 5) {
      return true;
    }
    if(number - firstEvent == 6) {
      return true;
    }
    if(number - firstEvent == 7) {
      return true;
    }
    if(number - firstEvent == 8) {
      return true;
    }
    if(number - firstEvent == 9) {
      return true;
    }
    if(number - firstEvent == 10) {
      return true;
    }
    if(number - firstEvent == 11) {
      return true;
    }
    return false;
  }

  @Override
  public boolean supportedError(byte code) {
    if(code - firstError == 0) {
      return true;
    }
    return false;
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    if(number - firstEvent == 0) {
      return NewKeyboardNotifyEvent.readNewKeyboardNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 1) {
      return MapNotifyEvent.readMapNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 2) {
      return StateNotifyEvent.readStateNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 3) {
      return ControlsNotifyEvent.readControlsNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 4) {
      return IndicatorStateNotifyEvent.readIndicatorStateNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 5) {
      return IndicatorMapNotifyEvent.readIndicatorMapNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 6) {
      return NamesNotifyEvent.readNamesNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 7) {
      return CompatMapNotifyEvent.readCompatMapNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 8) {
      return BellNotifyEvent.readBellNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 9) {
      return ActionMessageEvent.readActionMessageEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 10) {
      return AccessXNotifyEvent.readAccessXNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 11) {
      return ExtensionDeviceNotifyEvent.readExtensionDeviceNotifyEvent(firstEvent, sentEvent, in);
    }
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    if(code - firstError == 0) {
      return KeyboardError.readKeyboardError(firstError, in);
    }
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(boolean sentEvent, byte extension, short sequenceNumber,
      int length, short eventType, X11Input in) throws IOException {
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
