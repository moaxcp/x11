package com.github.moaxcp.x11.protocol.dri2;

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

public class Dri2Plugin implements XProtocolPlugin {
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
    return "dri2";
  }

  public Optional<String> getExtensionXName() {
    return Optional.ofNullable("DRI2");
  }

  public Optional<String> getExtensionName() {
    return Optional.ofNullable("DRI2");
  }

  public Optional<Boolean> getExtensionMultiword() {
    return Optional.ofNullable(true);
  }

  public Optional<Byte> getMajorVersion() {
    return Optional.ofNullable((byte) 1);
  }

  public Optional<Byte> getMinorVersion() {
    return Optional.ofNullable((byte) 4);
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
    return false;
  }

  @Override
  public XRequest readRequest(byte majorOpcode, byte minorOpcode, X11Input in) throws IOException {
    if(minorOpcode == QueryVersion.OPCODE) {
      return QueryVersion.readQueryVersion(in);
    }
    if(minorOpcode == Connect.OPCODE) {
      return Connect.readConnect(in);
    }
    if(minorOpcode == Authenticate.OPCODE) {
      return Authenticate.readAuthenticate(in);
    }
    if(minorOpcode == CreateDrawable.OPCODE) {
      return CreateDrawable.readCreateDrawable(in);
    }
    if(minorOpcode == DestroyDrawable.OPCODE) {
      return DestroyDrawable.readDestroyDrawable(in);
    }
    if(minorOpcode == GetBuffers.OPCODE) {
      return GetBuffers.readGetBuffers(in);
    }
    if(minorOpcode == CopyRegion.OPCODE) {
      return CopyRegion.readCopyRegion(in);
    }
    if(minorOpcode == GetBuffersWithFormat.OPCODE) {
      return GetBuffersWithFormat.readGetBuffersWithFormat(in);
    }
    if(minorOpcode == SwapBuffers.OPCODE) {
      return SwapBuffers.readSwapBuffers(in);
    }
    if(minorOpcode == GetMSC.OPCODE) {
      return GetMSC.readGetMSC(in);
    }
    if(minorOpcode == WaitMSC.OPCODE) {
      return WaitMSC.readWaitMSC(in);
    }
    if(minorOpcode == WaitSBC.OPCODE) {
      return WaitSBC.readWaitSBC(in);
    }
    if(minorOpcode == SwapInterval.OPCODE) {
      return SwapInterval.readSwapInterval(in);
    }
    if(minorOpcode == GetParam.OPCODE) {
      return GetParam.readGetParam(in);
    }
    throw new IllegalArgumentException("majorOpcode " + majorOpcode + ", minorOpcode " + minorOpcode + " is not supported");
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    if(number - firstEvent == 0) {
      return BufferSwapCompleteEvent.readBufferSwapCompleteEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 1) {
      return InvalidateBuffersEvent.readInvalidateBuffersEvent(firstEvent, sentEvent, in);
    }
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
