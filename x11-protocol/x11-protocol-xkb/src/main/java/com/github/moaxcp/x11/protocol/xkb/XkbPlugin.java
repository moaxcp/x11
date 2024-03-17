package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.XError;
import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.XGenericEvent;
import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.XRequest;
import java.io.IOException;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

public class XkbPlugin implements XProtocolPlugin {
  @Getter
  @Setter
  private byte majorOpcode;

  @Getter
  @Setter
  private byte firstEvent;

  @Getter
  @Setter
  private byte firstError;

  public String getPluginName() {
    return "xkb";
  }

  public Optional<String> getExtensionXName() {
    return Optional.ofNullable("XKEYBOARD");
  }

  public Optional<String> getExtensionName() {
    return Optional.ofNullable("xkb");
  }

  public Optional<Boolean> getExtensionMultiword() {
    return Optional.ofNullable(true);
  }

  public Optional<Byte> getMajorVersion() {
    return Optional.ofNullable((byte) 1);
  }

  public Optional<Byte> getMinorVersion() {
    return Optional.ofNullable((byte) 0);
  }

  @Override
  public boolean supportedRequest(XRequest request) {
    return request.getPluginName().equals(getPluginName());
  }

  @Override
  public boolean supportedRequest(byte majorOpcode, byte minorOpcode) {
    boolean isMajorOpcode = majorOpcode == getMajorOpcode();
    if(minorOpcode == 0) {
      return isMajorOpcode;
    }
    if(minorOpcode == 1) {
      return isMajorOpcode;
    }
    if(minorOpcode == 3) {
      return isMajorOpcode;
    }
    if(minorOpcode == 4) {
      return isMajorOpcode;
    }
    if(minorOpcode == 5) {
      return isMajorOpcode;
    }
    if(minorOpcode == 6) {
      return isMajorOpcode;
    }
    if(minorOpcode == 7) {
      return isMajorOpcode;
    }
    if(minorOpcode == 8) {
      return isMajorOpcode;
    }
    if(minorOpcode == 9) {
      return isMajorOpcode;
    }
    if(minorOpcode == 10) {
      return isMajorOpcode;
    }
    if(minorOpcode == 11) {
      return isMajorOpcode;
    }
    if(minorOpcode == 12) {
      return isMajorOpcode;
    }
    if(minorOpcode == 13) {
      return isMajorOpcode;
    }
    if(minorOpcode == 14) {
      return isMajorOpcode;
    }
    if(minorOpcode == 15) {
      return isMajorOpcode;
    }
    if(minorOpcode == 16) {
      return isMajorOpcode;
    }
    if(minorOpcode == 17) {
      return isMajorOpcode;
    }
    if(minorOpcode == 18) {
      return isMajorOpcode;
    }
    if(minorOpcode == 21) {
      return isMajorOpcode;
    }
    if(minorOpcode == 22) {
      return isMajorOpcode;
    }
    if(minorOpcode == 24) {
      return isMajorOpcode;
    }
    if(minorOpcode == 25) {
      return isMajorOpcode;
    }
    if(minorOpcode == 101) {
      return isMajorOpcode;
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
  public XRequest readRequest(byte majorOpcode, byte minorOpcode, X11Input in) throws IOException {
    if(minorOpcode == UseExtension.OPCODE) {
      return UseExtension.readUseExtension(in);
    }
    if(minorOpcode == SelectEvents.OPCODE) {
      return SelectEvents.readSelectEvents(in);
    }
    if(minorOpcode == Bell.OPCODE) {
      return Bell.readBell(in);
    }
    if(minorOpcode == GetState.OPCODE) {
      return GetState.readGetState(in);
    }
    if(minorOpcode == LatchLockState.OPCODE) {
      return LatchLockState.readLatchLockState(in);
    }
    if(minorOpcode == GetControls.OPCODE) {
      return GetControls.readGetControls(in);
    }
    if(minorOpcode == SetControls.OPCODE) {
      return SetControls.readSetControls(in);
    }
    if(minorOpcode == GetMap.OPCODE) {
      return GetMap.readGetMap(in);
    }
    if(minorOpcode == SetMap.OPCODE) {
      return SetMap.readSetMap(in);
    }
    if(minorOpcode == GetCompatMap.OPCODE) {
      return GetCompatMap.readGetCompatMap(in);
    }
    if(minorOpcode == SetCompatMap.OPCODE) {
      return SetCompatMap.readSetCompatMap(in);
    }
    if(minorOpcode == GetIndicatorState.OPCODE) {
      return GetIndicatorState.readGetIndicatorState(in);
    }
    if(minorOpcode == GetIndicatorMap.OPCODE) {
      return GetIndicatorMap.readGetIndicatorMap(in);
    }
    if(minorOpcode == SetIndicatorMap.OPCODE) {
      return SetIndicatorMap.readSetIndicatorMap(in);
    }
    if(minorOpcode == GetNamedIndicator.OPCODE) {
      return GetNamedIndicator.readGetNamedIndicator(in);
    }
    if(minorOpcode == SetNamedIndicator.OPCODE) {
      return SetNamedIndicator.readSetNamedIndicator(in);
    }
    if(minorOpcode == GetNames.OPCODE) {
      return GetNames.readGetNames(in);
    }
    if(minorOpcode == SetNames.OPCODE) {
      return SetNames.readSetNames(in);
    }
    if(minorOpcode == PerClientFlags.OPCODE) {
      return PerClientFlags.readPerClientFlags(in);
    }
    if(minorOpcode == ListComponents.OPCODE) {
      return ListComponents.readListComponents(in);
    }
    if(minorOpcode == GetDeviceInfo.OPCODE) {
      return GetDeviceInfo.readGetDeviceInfo(in);
    }
    if(minorOpcode == SetDeviceInfo.OPCODE) {
      return SetDeviceInfo.readSetDeviceInfo(in);
    }
    if(minorOpcode == SetDebuggingFlags.OPCODE) {
      return SetDebuggingFlags.readSetDebuggingFlags(in);
    }
    throw new IllegalArgumentException("majorOpcode " + majorOpcode + ", minorOpcode " + minorOpcode + " is not supported");
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
