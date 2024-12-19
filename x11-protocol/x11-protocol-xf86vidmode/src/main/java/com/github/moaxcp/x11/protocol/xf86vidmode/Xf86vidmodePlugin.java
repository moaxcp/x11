package com.github.moaxcp.x11.protocol.xf86vidmode;

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

public class Xf86vidmodePlugin implements XProtocolPlugin {
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
    return "xf86vidmode";
  }

  public Optional<String> getExtensionXName() {
    return Optional.ofNullable("XFree86-VidModeExtension");
  }

  public Optional<String> getExtensionName() {
    return Optional.ofNullable("XF86VidMode");
  }

  public Optional<Boolean> getExtensionMultiword() {
    return Optional.ofNullable(true);
  }

  public Optional<Byte> getMajorVersion() {
    return Optional.ofNullable((byte) 2);
  }

  public Optional<Byte> getMinorVersion() {
    return Optional.ofNullable((byte) 2);
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
  public XRequest readRequest(byte majorOpcode, byte minorOpcode, X11Input in) throws IOException {
    if(minorOpcode == QueryVersion.OPCODE) {
      return QueryVersion.readQueryVersion(in);
    }
    if(minorOpcode == GetModeLine.OPCODE) {
      return GetModeLine.readGetModeLine(in);
    }
    if(minorOpcode == ModModeLine.OPCODE) {
      return ModModeLine.readModModeLine(in);
    }
    if(minorOpcode == SwitchMode.OPCODE) {
      return SwitchMode.readSwitchMode(in);
    }
    if(minorOpcode == GetMonitor.OPCODE) {
      return GetMonitor.readGetMonitor(in);
    }
    if(minorOpcode == LockModeSwitch.OPCODE) {
      return LockModeSwitch.readLockModeSwitch(in);
    }
    if(minorOpcode == GetAllModeLines.OPCODE) {
      return GetAllModeLines.readGetAllModeLines(in);
    }
    if(minorOpcode == AddModeLine.OPCODE) {
      return AddModeLine.readAddModeLine(in);
    }
    if(minorOpcode == DeleteModeLine.OPCODE) {
      return DeleteModeLine.readDeleteModeLine(in);
    }
    if(minorOpcode == ValidateModeLine.OPCODE) {
      return ValidateModeLine.readValidateModeLine(in);
    }
    if(minorOpcode == SwitchToMode.OPCODE) {
      return SwitchToMode.readSwitchToMode(in);
    }
    if(minorOpcode == GetViewPort.OPCODE) {
      return GetViewPort.readGetViewPort(in);
    }
    if(minorOpcode == SetViewPort.OPCODE) {
      return SetViewPort.readSetViewPort(in);
    }
    if(minorOpcode == GetDotClocks.OPCODE) {
      return GetDotClocks.readGetDotClocks(in);
    }
    if(minorOpcode == SetClientVersion.OPCODE) {
      return SetClientVersion.readSetClientVersion(in);
    }
    if(minorOpcode == SetGamma.OPCODE) {
      return SetGamma.readSetGamma(in);
    }
    if(minorOpcode == GetGamma.OPCODE) {
      return GetGamma.readGetGamma(in);
    }
    if(minorOpcode == GetGammaRamp.OPCODE) {
      return GetGammaRamp.readGetGammaRamp(in);
    }
    if(minorOpcode == SetGammaRamp.OPCODE) {
      return SetGammaRamp.readSetGammaRamp(in);
    }
    if(minorOpcode == GetGammaRampSize.OPCODE) {
      return GetGammaRampSize.readGetGammaRampSize(in);
    }
    if(minorOpcode == GetPermissions.OPCODE) {
      return GetPermissions.readGetPermissions(in);
    }
    throw new IllegalArgumentException("majorOpcode " + majorOpcode + ", minorOpcode " + minorOpcode + " is not supported");
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
  public XGenericEvent readGenericEvent(byte firstEventOffset, boolean sentEvent, byte extension,
      short sequenceNumber, int length, short eventType, X11Input in) throws IOException {
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
