package com.github.moaxcp.x11.protocol.xf86dri;

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

public class Xf86driPlugin implements XProtocolPlugin {
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
    return "xf86dri";
  }

  public Optional<String> getExtensionXName() {
    return Optional.ofNullable("XFree86-DRI");
  }

  public Optional<String> getExtensionName() {
    return Optional.ofNullable("XF86Dri");
  }

  public Optional<Boolean> getExtensionMultiword() {
    return Optional.ofNullable(true);
  }

  public Optional<Byte> getMajorVersion() {
    return Optional.ofNullable((byte) 4);
  }

  public Optional<Byte> getMinorVersion() {
    return Optional.ofNullable((byte) 1);
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
    if(minorOpcode == QueryDirectRenderingCapable.OPCODE) {
      return QueryDirectRenderingCapable.readQueryDirectRenderingCapable(in);
    }
    if(minorOpcode == OpenConnection.OPCODE) {
      return OpenConnection.readOpenConnection(in);
    }
    if(minorOpcode == CloseConnection.OPCODE) {
      return CloseConnection.readCloseConnection(in);
    }
    if(minorOpcode == GetClientDriverName.OPCODE) {
      return GetClientDriverName.readGetClientDriverName(in);
    }
    if(minorOpcode == CreateContext.OPCODE) {
      return CreateContext.readCreateContext(in);
    }
    if(minorOpcode == DestroyContext.OPCODE) {
      return DestroyContext.readDestroyContext(in);
    }
    if(minorOpcode == CreateDrawable.OPCODE) {
      return CreateDrawable.readCreateDrawable(in);
    }
    if(minorOpcode == DestroyDrawable.OPCODE) {
      return DestroyDrawable.readDestroyDrawable(in);
    }
    if(minorOpcode == GetDrawableInfo.OPCODE) {
      return GetDrawableInfo.readGetDrawableInfo(in);
    }
    if(minorOpcode == GetDeviceInfo.OPCODE) {
      return GetDeviceInfo.readGetDeviceInfo(in);
    }
    if(minorOpcode == AuthConnection.OPCODE) {
      return AuthConnection.readAuthConnection(in);
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
