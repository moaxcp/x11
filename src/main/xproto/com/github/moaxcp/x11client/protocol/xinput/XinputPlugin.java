package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XError;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class XinputPlugin implements XProtocolPlugin {
  public static final String NAME = "XInputExtension";

  @Getter
  private byte majorVersion = 2;

  @Getter
  private byte minorVersion = 3;

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
    if(request instanceof GetExtensionVersion) {
      return true;
    }
    if(request instanceof ListInputDevices) {
      return true;
    }
    if(request instanceof OpenDevice) {
      return true;
    }
    if(request instanceof CloseDevice) {
      return true;
    }
    if(request instanceof SetDeviceMode) {
      return true;
    }
    if(request instanceof SelectExtensionEvent) {
      return true;
    }
    if(request instanceof GetSelectedExtensionEvents) {
      return true;
    }
    if(request instanceof ChangeDeviceDontPropagateList) {
      return true;
    }
    if(request instanceof GetDeviceDontPropagateList) {
      return true;
    }
    if(request instanceof GetDeviceMotionEvents) {
      return true;
    }
    if(request instanceof ChangeKeyboardDevice) {
      return true;
    }
    if(request instanceof ChangePointerDevice) {
      return true;
    }
    if(request instanceof GrabDevice) {
      return true;
    }
    if(request instanceof UngrabDevice) {
      return true;
    }
    if(request instanceof GrabDeviceKey) {
      return true;
    }
    if(request instanceof UngrabDeviceKey) {
      return true;
    }
    if(request instanceof GrabDeviceButton) {
      return true;
    }
    if(request instanceof UngrabDeviceButton) {
      return true;
    }
    if(request instanceof AllowDeviceEvents) {
      return true;
    }
    if(request instanceof GetDeviceFocus) {
      return true;
    }
    if(request instanceof SetDeviceFocus) {
      return true;
    }
    if(request instanceof GetFeedbackControl) {
      return true;
    }
    if(request instanceof ChangeFeedbackControl) {
      return true;
    }
    if(request instanceof GetDeviceKeyMapping) {
      return true;
    }
    if(request instanceof ChangeDeviceKeyMapping) {
      return true;
    }
    if(request instanceof GetDeviceModifierMapping) {
      return true;
    }
    if(request instanceof SetDeviceModifierMapping) {
      return true;
    }
    if(request instanceof GetDeviceButtonMapping) {
      return true;
    }
    if(request instanceof SetDeviceButtonMapping) {
      return true;
    }
    if(request instanceof QueryDeviceState) {
      return true;
    }
    if(request instanceof DeviceBell) {
      return true;
    }
    if(request instanceof SetDeviceValuators) {
      return true;
    }
    if(request instanceof GetDeviceControl) {
      return true;
    }
    if(request instanceof ChangeDeviceControl) {
      return true;
    }
    if(request instanceof ListDeviceProperties) {
      return true;
    }
    if(request instanceof ChangeDeviceProperty) {
      return true;
    }
    if(request instanceof DeleteDeviceProperty) {
      return true;
    }
    if(request instanceof GetDeviceProperty) {
      return true;
    }
    if(request instanceof XIQueryPointer) {
      return true;
    }
    if(request instanceof XIWarpPointer) {
      return true;
    }
    if(request instanceof XIChangeCursor) {
      return true;
    }
    if(request instanceof XIChangeHierarchy) {
      return true;
    }
    if(request instanceof XISetClientPointer) {
      return true;
    }
    if(request instanceof XIGetClientPointer) {
      return true;
    }
    if(request instanceof XISelectEvents) {
      return true;
    }
    if(request instanceof XIQueryVersion) {
      return true;
    }
    if(request instanceof XIQueryDevice) {
      return true;
    }
    if(request instanceof XISetFocus) {
      return true;
    }
    if(request instanceof XIGetFocus) {
      return true;
    }
    if(request instanceof XIGrabDevice) {
      return true;
    }
    if(request instanceof XIUngrabDevice) {
      return true;
    }
    if(request instanceof XIAllowEvents) {
      return true;
    }
    if(request instanceof XIPassiveGrabDevice) {
      return true;
    }
    if(request instanceof XIPassiveUngrabDevice) {
      return true;
    }
    if(request instanceof XIListProperties) {
      return true;
    }
    if(request instanceof XIChangeProperty) {
      return true;
    }
    if(request instanceof XIDeleteProperty) {
      return true;
    }
    if(request instanceof XIGetProperty) {
      return true;
    }
    if(request instanceof XIGetSelectedEvents) {
      return true;
    }
    if(request instanceof XIBarrierReleasePointer) {
      return true;
    }
    if(request instanceof SendExtensionEvent) {
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
    if(number - firstEvent == 12) {
      return true;
    }
    if(number - firstEvent == 13) {
      return true;
    }
    if(number - firstEvent == 14) {
      return true;
    }
    if(number - firstEvent == 15) {
      return true;
    }
    if(number - firstEvent == 16) {
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
    if(code - firstError == 4) {
      return true;
    }
    return false;
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    if(number - firstEvent == 0) {
      return DeviceValuatorEvent.readDeviceValuatorEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 1) {
      return DeviceKeyPressEvent.readDeviceKeyPressEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 2) {
      return DeviceKeyReleaseEvent.readDeviceKeyReleaseEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 3) {
      return DeviceButtonPressEvent.readDeviceButtonPressEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 4) {
      return DeviceButtonReleaseEvent.readDeviceButtonReleaseEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 5) {
      return DeviceMotionNotifyEvent.readDeviceMotionNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 6) {
      return DeviceFocusInEvent.readDeviceFocusInEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 7) {
      return DeviceFocusOutEvent.readDeviceFocusOutEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 8) {
      return ProximityInEvent.readProximityInEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 9) {
      return ProximityOutEvent.readProximityOutEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 10) {
      return DeviceStateNotifyEvent.readDeviceStateNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 11) {
      return DeviceMappingNotifyEvent.readDeviceMappingNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 12) {
      return ChangeDeviceNotifyEvent.readChangeDeviceNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 13) {
      return DeviceKeyStateNotifyEvent.readDeviceKeyStateNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 14) {
      return DeviceButtonStateNotifyEvent.readDeviceButtonStateNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 15) {
      return DevicePresenceNotifyEvent.readDevicePresenceNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 16) {
      return DevicePropertyNotifyEvent.readDevicePropertyNotifyEvent(firstEvent, sentEvent, in);
    }
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    if(code - firstError == 0) {
      return DeviceError.readDeviceError(firstError, in);
    }
    if(code - firstError == 1) {
      return EventError.readEventError(firstError, in);
    }
    if(code - firstError == 2) {
      return ModeError.readModeError(firstError, in);
    }
    if(code - firstError == 3) {
      return DeviceBusyError.readDeviceBusyError(firstError, in);
    }
    if(code - firstError == 4) {
      return ClassError.readClassError(firstError, in);
    }
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(boolean sentEvent, byte extension, short sequenceNumber,
      int length, short eventType, X11Input in) throws IOException {
    if(eventType == 1) {
    }
    if(eventType == 2) {
    }
    if(eventType == 2) {
    }
    if(eventType == 4) {
    }
    if(eventType == 4) {
    }
    if(eventType == 4) {
    }
    if(eventType == 7) {
    }
    if(eventType == 7) {
    }
    if(eventType == 7) {
    }
    if(eventType == 7) {
    }
    if(eventType == 11) {
    }
    if(eventType == 12) {
    }
    if(eventType == 13) {
    }
    if(eventType == 13) {
    }
    if(eventType == 15) {
    }
    if(eventType == 15) {
    }
    if(eventType == 15) {
    }
    if(eventType == 18) {
    }
    if(eventType == 18) {
    }
    if(eventType == 18) {
    }
    if(eventType == 21) {
    }
    if(eventType == 22) {
    }
    if(eventType == 22) {
    }
    if(eventType == 22) {
    }
    if(eventType == 25) {
    }
    if(eventType == 25) {
    }
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
