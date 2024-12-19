package com.github.moaxcp.x11.protocol.randr;

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

public class RandrPlugin implements XProtocolPlugin {
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
    return "randr";
  }

  public Optional<String> getExtensionXName() {
    return Optional.ofNullable("RANDR");
  }

  public Optional<String> getExtensionName() {
    return Optional.ofNullable("RandR");
  }

  public Optional<Boolean> getExtensionMultiword() {
    return Optional.ofNullable(true);
  }

  public Optional<Byte> getMajorVersion() {
    return Optional.ofNullable((byte) 1);
  }

  public Optional<Byte> getMinorVersion() {
    return Optional.ofNullable((byte) 6);
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
    if(minorOpcode == 2) {
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
    if(minorOpcode == 31) {
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
    if(code - firstError == 3) {
      return true;
    }
    return false;
  }

  @Override
  public XRequest readRequest(byte majorOpcode, byte minorOpcode, X11Input in) throws IOException {
    if(minorOpcode == QueryVersion.OPCODE) {
      return QueryVersion.readQueryVersion(in);
    }
    if(minorOpcode == SetScreenConfig.OPCODE) {
      return SetScreenConfig.readSetScreenConfig(in);
    }
    if(minorOpcode == SelectInput.OPCODE) {
      return SelectInput.readSelectInput(in);
    }
    if(minorOpcode == GetScreenInfo.OPCODE) {
      return GetScreenInfo.readGetScreenInfo(in);
    }
    if(minorOpcode == GetScreenSizeRange.OPCODE) {
      return GetScreenSizeRange.readGetScreenSizeRange(in);
    }
    if(minorOpcode == SetScreenSize.OPCODE) {
      return SetScreenSize.readSetScreenSize(in);
    }
    if(minorOpcode == GetScreenResources.OPCODE) {
      return GetScreenResources.readGetScreenResources(in);
    }
    if(minorOpcode == GetOutputInfo.OPCODE) {
      return GetOutputInfo.readGetOutputInfo(in);
    }
    if(minorOpcode == ListOutputProperties.OPCODE) {
      return ListOutputProperties.readListOutputProperties(in);
    }
    if(minorOpcode == QueryOutputProperty.OPCODE) {
      return QueryOutputProperty.readQueryOutputProperty(in);
    }
    if(minorOpcode == ConfigureOutputProperty.OPCODE) {
      return ConfigureOutputProperty.readConfigureOutputProperty(in);
    }
    if(minorOpcode == ChangeOutputProperty.OPCODE) {
      return ChangeOutputProperty.readChangeOutputProperty(in);
    }
    if(minorOpcode == DeleteOutputProperty.OPCODE) {
      return DeleteOutputProperty.readDeleteOutputProperty(in);
    }
    if(minorOpcode == GetOutputProperty.OPCODE) {
      return GetOutputProperty.readGetOutputProperty(in);
    }
    if(minorOpcode == CreateMode.OPCODE) {
      return CreateMode.readCreateMode(in);
    }
    if(minorOpcode == DestroyMode.OPCODE) {
      return DestroyMode.readDestroyMode(in);
    }
    if(minorOpcode == AddOutputMode.OPCODE) {
      return AddOutputMode.readAddOutputMode(in);
    }
    if(minorOpcode == DeleteOutputMode.OPCODE) {
      return DeleteOutputMode.readDeleteOutputMode(in);
    }
    if(minorOpcode == GetCrtcInfo.OPCODE) {
      return GetCrtcInfo.readGetCrtcInfo(in);
    }
    if(minorOpcode == SetCrtcConfig.OPCODE) {
      return SetCrtcConfig.readSetCrtcConfig(in);
    }
    if(minorOpcode == GetCrtcGammaSize.OPCODE) {
      return GetCrtcGammaSize.readGetCrtcGammaSize(in);
    }
    if(minorOpcode == GetCrtcGamma.OPCODE) {
      return GetCrtcGamma.readGetCrtcGamma(in);
    }
    if(minorOpcode == SetCrtcGamma.OPCODE) {
      return SetCrtcGamma.readSetCrtcGamma(in);
    }
    if(minorOpcode == GetScreenResourcesCurrent.OPCODE) {
      return GetScreenResourcesCurrent.readGetScreenResourcesCurrent(in);
    }
    if(minorOpcode == SetCrtcTransform.OPCODE) {
      return SetCrtcTransform.readSetCrtcTransform(in);
    }
    if(minorOpcode == GetCrtcTransform.OPCODE) {
      return GetCrtcTransform.readGetCrtcTransform(in);
    }
    if(minorOpcode == GetPanning.OPCODE) {
      return GetPanning.readGetPanning(in);
    }
    if(minorOpcode == SetPanning.OPCODE) {
      return SetPanning.readSetPanning(in);
    }
    if(minorOpcode == SetOutputPrimary.OPCODE) {
      return SetOutputPrimary.readSetOutputPrimary(in);
    }
    if(minorOpcode == GetOutputPrimary.OPCODE) {
      return GetOutputPrimary.readGetOutputPrimary(in);
    }
    if(minorOpcode == GetProviders.OPCODE) {
      return GetProviders.readGetProviders(in);
    }
    if(minorOpcode == GetProviderInfo.OPCODE) {
      return GetProviderInfo.readGetProviderInfo(in);
    }
    if(minorOpcode == SetProviderOffloadSink.OPCODE) {
      return SetProviderOffloadSink.readSetProviderOffloadSink(in);
    }
    if(minorOpcode == SetProviderOutputSource.OPCODE) {
      return SetProviderOutputSource.readSetProviderOutputSource(in);
    }
    if(minorOpcode == ListProviderProperties.OPCODE) {
      return ListProviderProperties.readListProviderProperties(in);
    }
    if(minorOpcode == QueryProviderProperty.OPCODE) {
      return QueryProviderProperty.readQueryProviderProperty(in);
    }
    if(minorOpcode == ConfigureProviderProperty.OPCODE) {
      return ConfigureProviderProperty.readConfigureProviderProperty(in);
    }
    if(minorOpcode == ChangeProviderProperty.OPCODE) {
      return ChangeProviderProperty.readChangeProviderProperty(in);
    }
    if(minorOpcode == DeleteProviderProperty.OPCODE) {
      return DeleteProviderProperty.readDeleteProviderProperty(in);
    }
    if(minorOpcode == GetProviderProperty.OPCODE) {
      return GetProviderProperty.readGetProviderProperty(in);
    }
    if(minorOpcode == GetMonitors.OPCODE) {
      return GetMonitors.readGetMonitors(in);
    }
    if(minorOpcode == SetMonitor.OPCODE) {
      return SetMonitor.readSetMonitor(in);
    }
    if(minorOpcode == DeleteMonitor.OPCODE) {
      return DeleteMonitor.readDeleteMonitor(in);
    }
    if(minorOpcode == CreateLease.OPCODE) {
      return CreateLease.readCreateLease(in);
    }
    if(minorOpcode == FreeLease.OPCODE) {
      return FreeLease.readFreeLease(in);
    }
    throw new IllegalArgumentException("majorOpcode " + majorOpcode + ", minorOpcode " + minorOpcode + " is not supported");
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    if(number - firstEvent == 0) {
      return ScreenChangeNotifyEvent.readScreenChangeNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 1) {
      return NotifyEvent.readNotifyEvent(firstEvent, sentEvent, in);
    }
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    if(code - firstError == 0) {
      return BadOutputError.readBadOutputError(firstError, in);
    }
    if(code - firstError == 1) {
      return BadCrtcError.readBadCrtcError(firstError, in);
    }
    if(code - firstError == 2) {
      return BadModeError.readBadModeError(firstError, in);
    }
    if(code - firstError == 3) {
      return BadProviderError.readBadProviderError(firstError, in);
    }
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(byte firstEventOffset, boolean sentEvent, byte extension,
      short sequenceNumber, int length, short eventType, X11Input in) throws IOException {
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
