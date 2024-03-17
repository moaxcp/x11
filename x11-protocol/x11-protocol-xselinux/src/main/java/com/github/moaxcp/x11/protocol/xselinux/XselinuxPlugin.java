package com.github.moaxcp.x11.protocol.xselinux;

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

public class XselinuxPlugin implements XProtocolPlugin {
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
    return "xselinux";
  }

  public Optional<String> getExtensionXName() {
    return Optional.ofNullable("SELinux");
  }

  public Optional<String> getExtensionName() {
    return Optional.ofNullable("SELinux");
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
    if(minorOpcode == QueryVersion.OPCODE) {
      return QueryVersion.readQueryVersion(in);
    }
    if(minorOpcode == SetDeviceCreateContext.OPCODE) {
      return SetDeviceCreateContext.readSetDeviceCreateContext(in);
    }
    if(minorOpcode == GetDeviceCreateContext.OPCODE) {
      return GetDeviceCreateContext.readGetDeviceCreateContext(in);
    }
    if(minorOpcode == SetDeviceContext.OPCODE) {
      return SetDeviceContext.readSetDeviceContext(in);
    }
    if(minorOpcode == GetDeviceContext.OPCODE) {
      return GetDeviceContext.readGetDeviceContext(in);
    }
    if(minorOpcode == SetWindowCreateContext.OPCODE) {
      return SetWindowCreateContext.readSetWindowCreateContext(in);
    }
    if(minorOpcode == GetWindowCreateContext.OPCODE) {
      return GetWindowCreateContext.readGetWindowCreateContext(in);
    }
    if(minorOpcode == GetWindowContext.OPCODE) {
      return GetWindowContext.readGetWindowContext(in);
    }
    if(minorOpcode == SetPropertyCreateContext.OPCODE) {
      return SetPropertyCreateContext.readSetPropertyCreateContext(in);
    }
    if(minorOpcode == GetPropertyCreateContext.OPCODE) {
      return GetPropertyCreateContext.readGetPropertyCreateContext(in);
    }
    if(minorOpcode == SetPropertyUseContext.OPCODE) {
      return SetPropertyUseContext.readSetPropertyUseContext(in);
    }
    if(minorOpcode == GetPropertyUseContext.OPCODE) {
      return GetPropertyUseContext.readGetPropertyUseContext(in);
    }
    if(minorOpcode == GetPropertyContext.OPCODE) {
      return GetPropertyContext.readGetPropertyContext(in);
    }
    if(minorOpcode == GetPropertyDataContext.OPCODE) {
      return GetPropertyDataContext.readGetPropertyDataContext(in);
    }
    if(minorOpcode == ListProperties.OPCODE) {
      return ListProperties.readListProperties(in);
    }
    if(minorOpcode == SetSelectionCreateContext.OPCODE) {
      return SetSelectionCreateContext.readSetSelectionCreateContext(in);
    }
    if(minorOpcode == GetSelectionCreateContext.OPCODE) {
      return GetSelectionCreateContext.readGetSelectionCreateContext(in);
    }
    if(minorOpcode == SetSelectionUseContext.OPCODE) {
      return SetSelectionUseContext.readSetSelectionUseContext(in);
    }
    if(minorOpcode == GetSelectionUseContext.OPCODE) {
      return GetSelectionUseContext.readGetSelectionUseContext(in);
    }
    if(minorOpcode == GetSelectionContext.OPCODE) {
      return GetSelectionContext.readGetSelectionContext(in);
    }
    if(minorOpcode == GetSelectionDataContext.OPCODE) {
      return GetSelectionDataContext.readGetSelectionDataContext(in);
    }
    if(minorOpcode == ListSelections.OPCODE) {
      return ListSelections.readListSelections(in);
    }
    if(minorOpcode == GetClientContext.OPCODE) {
      return GetClientContext.readGetClientContext(in);
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
  public XGenericEvent readGenericEvent(boolean sentEvent, byte extension, short sequenceNumber,
      int length, short eventType, X11Input in) throws IOException {
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
