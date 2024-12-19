package com.github.moaxcp.x11.protocol.dpms;

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

public class DpmsPlugin implements XProtocolPlugin {
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
    return "dpms";
  }

  public Optional<String> getExtensionXName() {
    return Optional.ofNullable("DPMS");
  }

  public Optional<String> getExtensionName() {
    return Optional.ofNullable("DPMS");
  }

  public Optional<Boolean> getExtensionMultiword() {
    return Optional.ofNullable(true);
  }

  public Optional<Byte> getMajorVersion() {
    return Optional.ofNullable((byte) 0);
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
    return false;
  }

  @Override
  public boolean supportedEvent(byte number) {
    return false;
  }

  @Override
  public boolean supportedError(byte code) {
    return false;
  }

  @Override
  public XRequest readRequest(byte majorOpcode, byte minorOpcode, X11Input in) throws IOException {
    if(minorOpcode == GetVersion.OPCODE) {
      return GetVersion.readGetVersion(in);
    }
    if(minorOpcode == Capable.OPCODE) {
      return Capable.readCapable(in);
    }
    if(minorOpcode == GetTimeouts.OPCODE) {
      return GetTimeouts.readGetTimeouts(in);
    }
    if(minorOpcode == SetTimeouts.OPCODE) {
      return SetTimeouts.readSetTimeouts(in);
    }
    if(minorOpcode == Enable.OPCODE) {
      return Enable.readEnable(in);
    }
    if(minorOpcode == Disable.OPCODE) {
      return Disable.readDisable(in);
    }
    if(minorOpcode == ForceLevel.OPCODE) {
      return ForceLevel.readForceLevel(in);
    }
    if(minorOpcode == Info.OPCODE) {
      return Info.readInfo(in);
    }
    throw new IllegalArgumentException("majorOpcode " + majorOpcode + ", minorOpcode " + minorOpcode + " is not supported");
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(byte firstEventOffset, boolean sentEvent, byte extension,
      short sequenceNumber, int length, short eventType, X11Input in) throws IOException {
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
