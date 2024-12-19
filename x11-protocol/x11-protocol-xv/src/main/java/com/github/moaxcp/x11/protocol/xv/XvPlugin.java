package com.github.moaxcp.x11.protocol.xv;

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

public class XvPlugin implements XProtocolPlugin {
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
    return "xv";
  }

  public Optional<String> getExtensionXName() {
    return Optional.ofNullable("XVideo");
  }

  public Optional<String> getExtensionName() {
    return Optional.ofNullable("Xv");
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
    if(code - firstError == 0) {
      return true;
    }
    if(code - firstError == 1) {
      return true;
    }
    if(code - firstError == 2) {
      return true;
    }
    return false;
  }

  @Override
  public XRequest readRequest(byte majorOpcode, byte minorOpcode, X11Input in) throws IOException {
    if(minorOpcode == QueryExtension.OPCODE) {
      return QueryExtension.readQueryExtension(in);
    }
    if(minorOpcode == QueryAdaptors.OPCODE) {
      return QueryAdaptors.readQueryAdaptors(in);
    }
    if(minorOpcode == QueryEncodings.OPCODE) {
      return QueryEncodings.readQueryEncodings(in);
    }
    if(minorOpcode == GrabPort.OPCODE) {
      return GrabPort.readGrabPort(in);
    }
    if(minorOpcode == UngrabPort.OPCODE) {
      return UngrabPort.readUngrabPort(in);
    }
    if(minorOpcode == PutVideo.OPCODE) {
      return PutVideo.readPutVideo(in);
    }
    if(minorOpcode == PutStill.OPCODE) {
      return PutStill.readPutStill(in);
    }
    if(minorOpcode == GetVideo.OPCODE) {
      return GetVideo.readGetVideo(in);
    }
    if(minorOpcode == GetStill.OPCODE) {
      return GetStill.readGetStill(in);
    }
    if(minorOpcode == StopVideo.OPCODE) {
      return StopVideo.readStopVideo(in);
    }
    if(minorOpcode == SelectVideoNotify.OPCODE) {
      return SelectVideoNotify.readSelectVideoNotify(in);
    }
    if(minorOpcode == SelectPortNotify.OPCODE) {
      return SelectPortNotify.readSelectPortNotify(in);
    }
    if(minorOpcode == QueryBestSize.OPCODE) {
      return QueryBestSize.readQueryBestSize(in);
    }
    if(minorOpcode == SetPortAttribute.OPCODE) {
      return SetPortAttribute.readSetPortAttribute(in);
    }
    if(minorOpcode == GetPortAttribute.OPCODE) {
      return GetPortAttribute.readGetPortAttribute(in);
    }
    if(minorOpcode == QueryPortAttributes.OPCODE) {
      return QueryPortAttributes.readQueryPortAttributes(in);
    }
    if(minorOpcode == ListImageFormats.OPCODE) {
      return ListImageFormats.readListImageFormats(in);
    }
    if(minorOpcode == QueryImageAttributes.OPCODE) {
      return QueryImageAttributes.readQueryImageAttributes(in);
    }
    if(minorOpcode == PutImage.OPCODE) {
      return PutImage.readPutImage(in);
    }
    if(minorOpcode == ShmPutImage.OPCODE) {
      return ShmPutImage.readShmPutImage(in);
    }
    throw new IllegalArgumentException("majorOpcode " + majorOpcode + ", minorOpcode " + minorOpcode + " is not supported");
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    if(number - firstEvent == 0) {
      return VideoNotifyEvent.readVideoNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 1) {
      return PortNotifyEvent.readPortNotifyEvent(firstEvent, sentEvent, in);
    }
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    if(code - firstError == 0) {
      return BadPortError.readBadPortError(firstError, in);
    }
    if(code - firstError == 1) {
      return BadEncodingError.readBadEncodingError(firstError, in);
    }
    if(code - firstError == 2) {
      return BadControlError.readBadControlError(firstError, in);
    }
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(byte firstEventOffset, boolean sentEvent, byte extension,
      short sequenceNumber, int length, short eventType, X11Input in) throws IOException {
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
