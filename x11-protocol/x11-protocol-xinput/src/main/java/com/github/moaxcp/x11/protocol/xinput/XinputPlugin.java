package com.github.moaxcp.x11.protocol.xinput;

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

public class XinputPlugin implements XProtocolPlugin {
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
    return "xinput";
  }

  public Optional<String> getExtensionXName() {
    return Optional.ofNullable("XInputExtension");
  }

  public Optional<String> getExtensionName() {
    return Optional.ofNullable("Input");
  }

  public Optional<Boolean> getExtensionMultiword() {
    return Optional.ofNullable(true);
  }

  public Optional<Byte> getMajorVersion() {
    return Optional.ofNullable((byte) 2);
  }

  public Optional<Byte> getMinorVersion() {
    return Optional.ofNullable((byte) 3);
  }

  @Override
  public boolean supportedRequest(XRequest request) {
    return request.getPluginName().equals(getPluginName());
  }

  @Override
  public boolean supportedRequest(byte majorOpcode, byte minorOpcode) {
    boolean isMajorOpcode = majorOpcode == getMajorOpcode();
    if(minorOpcode == 1) {
      return isMajorOpcode;
    }
    if(minorOpcode == 2) {
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
    if(minorOpcode == 19) {
      return isMajorOpcode;
    }
    if(minorOpcode == 20) {
      return isMajorOpcode;
    }
    if(minorOpcode == 21) {
      return isMajorOpcode;
    }
    if(minorOpcode == 22) {
      return isMajorOpcode;
    }
    if(minorOpcode == 23) {
      return isMajorOpcode;
    }
    if(minorOpcode == 24) {
      return isMajorOpcode;
    }
    if(minorOpcode == 25) {
      return isMajorOpcode;
    }
    if(minorOpcode == 26) {
      return isMajorOpcode;
    }
    if(minorOpcode == 27) {
      return isMajorOpcode;
    }
    if(minorOpcode == 28) {
      return isMajorOpcode;
    }
    if(minorOpcode == 29) {
      return isMajorOpcode;
    }
    if(minorOpcode == 30) {
      return isMajorOpcode;
    }
    if(minorOpcode == 32) {
      return isMajorOpcode;
    }
    if(minorOpcode == 33) {
      return isMajorOpcode;
    }
    if(minorOpcode == 34) {
      return isMajorOpcode;
    }
    if(minorOpcode == 35) {
      return isMajorOpcode;
    }
    if(minorOpcode == 36) {
      return isMajorOpcode;
    }
    if(minorOpcode == 37) {
      return isMajorOpcode;
    }
    if(minorOpcode == 38) {
      return isMajorOpcode;
    }
    if(minorOpcode == 39) {
      return isMajorOpcode;
    }
    if(minorOpcode == 40) {
      return isMajorOpcode;
    }
    if(minorOpcode == 41) {
      return isMajorOpcode;
    }
    if(minorOpcode == 42) {
      return isMajorOpcode;
    }
    if(minorOpcode == 43) {
      return isMajorOpcode;
    }
    if(minorOpcode == 44) {
      return isMajorOpcode;
    }
    if(minorOpcode == 45) {
      return isMajorOpcode;
    }
    if(minorOpcode == 46) {
      return isMajorOpcode;
    }
    if(minorOpcode == 47) {
      return isMajorOpcode;
    }
    if(minorOpcode == 48) {
      return isMajorOpcode;
    }
    if(minorOpcode == 49) {
      return isMajorOpcode;
    }
    if(minorOpcode == 50) {
      return isMajorOpcode;
    }
    if(minorOpcode == 51) {
      return isMajorOpcode;
    }
    if(minorOpcode == 52) {
      return isMajorOpcode;
    }
    if(minorOpcode == 53) {
      return isMajorOpcode;
    }
    if(minorOpcode == 54) {
      return isMajorOpcode;
    }
    if(minorOpcode == 55) {
      return isMajorOpcode;
    }
    if(minorOpcode == 56) {
      return isMajorOpcode;
    }
    if(minorOpcode == 57) {
      return isMajorOpcode;
    }
    if(minorOpcode == 58) {
      return isMajorOpcode;
    }
    if(minorOpcode == 59) {
      return isMajorOpcode;
    }
    if(minorOpcode == 60) {
      return isMajorOpcode;
    }
    if(minorOpcode == 61) {
      return isMajorOpcode;
    }
    if(minorOpcode == 31) {
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
  public XRequest readRequest(byte majorOpcode, byte minorOpcode, X11Input in) throws IOException {
    if(minorOpcode == GetExtensionVersion.OPCODE) {
      return GetExtensionVersion.readGetExtensionVersion(in);
    }
    if(minorOpcode == ListInputDevices.OPCODE) {
      return ListInputDevices.readListInputDevices(in);
    }
    if(minorOpcode == OpenDevice.OPCODE) {
      return OpenDevice.readOpenDevice(in);
    }
    if(minorOpcode == CloseDevice.OPCODE) {
      return CloseDevice.readCloseDevice(in);
    }
    if(minorOpcode == SetDeviceMode.OPCODE) {
      return SetDeviceMode.readSetDeviceMode(in);
    }
    if(minorOpcode == SelectExtensionEvent.OPCODE) {
      return SelectExtensionEvent.readSelectExtensionEvent(in);
    }
    if(minorOpcode == GetSelectedExtensionEvents.OPCODE) {
      return GetSelectedExtensionEvents.readGetSelectedExtensionEvents(in);
    }
    if(minorOpcode == ChangeDeviceDontPropagateList.OPCODE) {
      return ChangeDeviceDontPropagateList.readChangeDeviceDontPropagateList(in);
    }
    if(minorOpcode == GetDeviceDontPropagateList.OPCODE) {
      return GetDeviceDontPropagateList.readGetDeviceDontPropagateList(in);
    }
    if(minorOpcode == GetDeviceMotionEvents.OPCODE) {
      return GetDeviceMotionEvents.readGetDeviceMotionEvents(in);
    }
    if(minorOpcode == ChangeKeyboardDevice.OPCODE) {
      return ChangeKeyboardDevice.readChangeKeyboardDevice(in);
    }
    if(minorOpcode == ChangePointerDevice.OPCODE) {
      return ChangePointerDevice.readChangePointerDevice(in);
    }
    if(minorOpcode == GrabDevice.OPCODE) {
      return GrabDevice.readGrabDevice(in);
    }
    if(minorOpcode == UngrabDevice.OPCODE) {
      return UngrabDevice.readUngrabDevice(in);
    }
    if(minorOpcode == GrabDeviceKey.OPCODE) {
      return GrabDeviceKey.readGrabDeviceKey(in);
    }
    if(minorOpcode == UngrabDeviceKey.OPCODE) {
      return UngrabDeviceKey.readUngrabDeviceKey(in);
    }
    if(minorOpcode == GrabDeviceButton.OPCODE) {
      return GrabDeviceButton.readGrabDeviceButton(in);
    }
    if(minorOpcode == UngrabDeviceButton.OPCODE) {
      return UngrabDeviceButton.readUngrabDeviceButton(in);
    }
    if(minorOpcode == AllowDeviceEvents.OPCODE) {
      return AllowDeviceEvents.readAllowDeviceEvents(in);
    }
    if(minorOpcode == GetDeviceFocus.OPCODE) {
      return GetDeviceFocus.readGetDeviceFocus(in);
    }
    if(minorOpcode == SetDeviceFocus.OPCODE) {
      return SetDeviceFocus.readSetDeviceFocus(in);
    }
    if(minorOpcode == GetFeedbackControl.OPCODE) {
      return GetFeedbackControl.readGetFeedbackControl(in);
    }
    if(minorOpcode == ChangeFeedbackControl.OPCODE) {
      return ChangeFeedbackControl.readChangeFeedbackControl(in);
    }
    if(minorOpcode == GetDeviceKeyMapping.OPCODE) {
      return GetDeviceKeyMapping.readGetDeviceKeyMapping(in);
    }
    if(minorOpcode == ChangeDeviceKeyMapping.OPCODE) {
      return ChangeDeviceKeyMapping.readChangeDeviceKeyMapping(in);
    }
    if(minorOpcode == GetDeviceModifierMapping.OPCODE) {
      return GetDeviceModifierMapping.readGetDeviceModifierMapping(in);
    }
    if(minorOpcode == SetDeviceModifierMapping.OPCODE) {
      return SetDeviceModifierMapping.readSetDeviceModifierMapping(in);
    }
    if(minorOpcode == GetDeviceButtonMapping.OPCODE) {
      return GetDeviceButtonMapping.readGetDeviceButtonMapping(in);
    }
    if(minorOpcode == SetDeviceButtonMapping.OPCODE) {
      return SetDeviceButtonMapping.readSetDeviceButtonMapping(in);
    }
    if(minorOpcode == QueryDeviceState.OPCODE) {
      return QueryDeviceState.readQueryDeviceState(in);
    }
    if(minorOpcode == DeviceBell.OPCODE) {
      return DeviceBell.readDeviceBell(in);
    }
    if(minorOpcode == SetDeviceValuators.OPCODE) {
      return SetDeviceValuators.readSetDeviceValuators(in);
    }
    if(minorOpcode == GetDeviceControl.OPCODE) {
      return GetDeviceControl.readGetDeviceControl(in);
    }
    if(minorOpcode == ChangeDeviceControl.OPCODE) {
      return ChangeDeviceControl.readChangeDeviceControl(in);
    }
    if(minorOpcode == ListDeviceProperties.OPCODE) {
      return ListDeviceProperties.readListDeviceProperties(in);
    }
    if(minorOpcode == ChangeDeviceProperty.OPCODE) {
      return ChangeDeviceProperty.readChangeDeviceProperty(in);
    }
    if(minorOpcode == DeleteDeviceProperty.OPCODE) {
      return DeleteDeviceProperty.readDeleteDeviceProperty(in);
    }
    if(minorOpcode == GetDeviceProperty.OPCODE) {
      return GetDeviceProperty.readGetDeviceProperty(in);
    }
    if(minorOpcode == XIQueryPointer.OPCODE) {
      return XIQueryPointer.readXIQueryPointer(in);
    }
    if(minorOpcode == XIWarpPointer.OPCODE) {
      return XIWarpPointer.readXIWarpPointer(in);
    }
    if(minorOpcode == XIChangeCursor.OPCODE) {
      return XIChangeCursor.readXIChangeCursor(in);
    }
    if(minorOpcode == XIChangeHierarchy.OPCODE) {
      return XIChangeHierarchy.readXIChangeHierarchy(in);
    }
    if(minorOpcode == XISetClientPointer.OPCODE) {
      return XISetClientPointer.readXISetClientPointer(in);
    }
    if(minorOpcode == XIGetClientPointer.OPCODE) {
      return XIGetClientPointer.readXIGetClientPointer(in);
    }
    if(minorOpcode == XISelectEvents.OPCODE) {
      return XISelectEvents.readXISelectEvents(in);
    }
    if(minorOpcode == XIQueryVersion.OPCODE) {
      return XIQueryVersion.readXIQueryVersion(in);
    }
    if(minorOpcode == XIQueryDevice.OPCODE) {
      return XIQueryDevice.readXIQueryDevice(in);
    }
    if(minorOpcode == XISetFocus.OPCODE) {
      return XISetFocus.readXISetFocus(in);
    }
    if(minorOpcode == XIGetFocus.OPCODE) {
      return XIGetFocus.readXIGetFocus(in);
    }
    if(minorOpcode == XIGrabDevice.OPCODE) {
      return XIGrabDevice.readXIGrabDevice(in);
    }
    if(minorOpcode == XIUngrabDevice.OPCODE) {
      return XIUngrabDevice.readXIUngrabDevice(in);
    }
    if(minorOpcode == XIAllowEvents.OPCODE) {
      return XIAllowEvents.readXIAllowEvents(in);
    }
    if(minorOpcode == XIPassiveGrabDevice.OPCODE) {
      return XIPassiveGrabDevice.readXIPassiveGrabDevice(in);
    }
    if(minorOpcode == XIPassiveUngrabDevice.OPCODE) {
      return XIPassiveUngrabDevice.readXIPassiveUngrabDevice(in);
    }
    if(minorOpcode == XIListProperties.OPCODE) {
      return XIListProperties.readXIListProperties(in);
    }
    if(minorOpcode == XIChangeProperty.OPCODE) {
      return XIChangeProperty.readXIChangeProperty(in);
    }
    if(minorOpcode == XIDeleteProperty.OPCODE) {
      return XIDeleteProperty.readXIDeleteProperty(in);
    }
    if(minorOpcode == XIGetProperty.OPCODE) {
      return XIGetProperty.readXIGetProperty(in);
    }
    if(minorOpcode == XIGetSelectedEvents.OPCODE) {
      return XIGetSelectedEvents.readXIGetSelectedEvents(in);
    }
    if(minorOpcode == XIBarrierReleasePointer.OPCODE) {
      return XIBarrierReleasePointer.readXIBarrierReleasePointer(in);
    }
    if(minorOpcode == SendExtensionEvent.OPCODE) {
      return SendExtensionEvent.readSendExtensionEvent(in);
    }
    throw new IllegalArgumentException("majorOpcode " + majorOpcode + ", minorOpcode " + minorOpcode + " is not supported");
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
  public XGenericEvent readGenericEvent(byte firstEventOffset, boolean sentEvent, byte extension,
      short sequenceNumber, int length, short eventType, X11Input in) throws IOException {
    if(eventType == 1) {
      return DeviceChangedEvent.readDeviceChangedEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 2) {
      return KeyPressEvent.readKeyPressEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 2) {
      return KeyReleaseEvent.readKeyReleaseEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 4) {
      return ButtonPressEvent.readButtonPressEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 4) {
      return ButtonReleaseEvent.readButtonReleaseEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 4) {
      return MotionEvent.readMotionEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 7) {
      return EnterEvent.readEnterEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 7) {
      return LeaveEvent.readLeaveEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 7) {
      return FocusInEvent.readFocusInEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 7) {
      return FocusOutEvent.readFocusOutEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 11) {
      return HierarchyEvent.readHierarchyEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 12) {
      return PropertyEvent.readPropertyEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 13) {
      return RawKeyPressEvent.readRawKeyPressEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 13) {
      return RawKeyReleaseEvent.readRawKeyReleaseEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 15) {
      return RawButtonPressEvent.readRawButtonPressEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 15) {
      return RawButtonReleaseEvent.readRawButtonReleaseEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 15) {
      return RawMotionEvent.readRawMotionEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 18) {
      return TouchBeginEvent.readTouchBeginEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 18) {
      return TouchUpdateEvent.readTouchUpdateEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 18) {
      return TouchEndEvent.readTouchEndEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 21) {
      return TouchOwnershipEvent.readTouchOwnershipEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 22) {
      return RawTouchBeginEvent.readRawTouchBeginEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 22) {
      return RawTouchUpdateEvent.readRawTouchUpdateEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 22) {
      return RawTouchEndEvent.readRawTouchEndEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 25) {
      return BarrierHitEvent.readBarrierHitEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    if(eventType == 25) {
      return BarrierLeaveEvent.readBarrierLeaveEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
