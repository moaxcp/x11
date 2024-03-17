package com.github.moaxcp.x11.protocol.sync;

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

public class SyncPlugin implements XProtocolPlugin {
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
    return "sync";
  }

  public Optional<String> getExtensionXName() {
    return Optional.ofNullable("SYNC");
  }

  public Optional<String> getExtensionName() {
    return Optional.ofNullable("Sync");
  }

  public Optional<Boolean> getExtensionMultiword() {
    return Optional.ofNullable(true);
  }

  public Optional<Byte> getMajorVersion() {
    return Optional.ofNullable((byte) 3);
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
    if(minorOpcode == 6) {
      return isMajorOpcode;
    }
    if(minorOpcode == 5) {
      return isMajorOpcode;
    }
    if(minorOpcode == 7) {
      return isMajorOpcode;
    }
    if(minorOpcode == 4) {
      return isMajorOpcode;
    }
    if(minorOpcode == 3) {
      return isMajorOpcode;
    }
    if(minorOpcode == 8) {
      return isMajorOpcode;
    }
    if(minorOpcode == 9) {
      return isMajorOpcode;
    }
    if(minorOpcode == 11) {
      return isMajorOpcode;
    }
    if(minorOpcode == 10) {
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
    return false;
  }

  @Override
  public XRequest readRequest(byte majorOpcode, byte minorOpcode, X11Input in) throws IOException {
    if(minorOpcode == Initialize.OPCODE) {
      return Initialize.readInitialize(in);
    }
    if(minorOpcode == ListSystemCounters.OPCODE) {
      return ListSystemCounters.readListSystemCounters(in);
    }
    if(minorOpcode == CreateCounter.OPCODE) {
      return CreateCounter.readCreateCounter(in);
    }
    if(minorOpcode == DestroyCounter.OPCODE) {
      return DestroyCounter.readDestroyCounter(in);
    }
    if(minorOpcode == QueryCounter.OPCODE) {
      return QueryCounter.readQueryCounter(in);
    }
    if(minorOpcode == Await.OPCODE) {
      return Await.readAwait(in);
    }
    if(minorOpcode == ChangeCounter.OPCODE) {
      return ChangeCounter.readChangeCounter(in);
    }
    if(minorOpcode == SetCounter.OPCODE) {
      return SetCounter.readSetCounter(in);
    }
    if(minorOpcode == CreateAlarm.OPCODE) {
      return CreateAlarm.readCreateAlarm(in);
    }
    if(minorOpcode == ChangeAlarm.OPCODE) {
      return ChangeAlarm.readChangeAlarm(in);
    }
    if(minorOpcode == DestroyAlarm.OPCODE) {
      return DestroyAlarm.readDestroyAlarm(in);
    }
    if(minorOpcode == QueryAlarm.OPCODE) {
      return QueryAlarm.readQueryAlarm(in);
    }
    if(minorOpcode == SetPriority.OPCODE) {
      return SetPriority.readSetPriority(in);
    }
    if(minorOpcode == GetPriority.OPCODE) {
      return GetPriority.readGetPriority(in);
    }
    if(minorOpcode == CreateFence.OPCODE) {
      return CreateFence.readCreateFence(in);
    }
    if(minorOpcode == TriggerFence.OPCODE) {
      return TriggerFence.readTriggerFence(in);
    }
    if(minorOpcode == ResetFence.OPCODE) {
      return ResetFence.readResetFence(in);
    }
    if(minorOpcode == DestroyFence.OPCODE) {
      return DestroyFence.readDestroyFence(in);
    }
    if(minorOpcode == QueryFence.OPCODE) {
      return QueryFence.readQueryFence(in);
    }
    if(minorOpcode == AwaitFence.OPCODE) {
      return AwaitFence.readAwaitFence(in);
    }
    throw new IllegalArgumentException("majorOpcode " + majorOpcode + ", minorOpcode " + minorOpcode + " is not supported");
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    if(number - firstEvent == 0) {
      return CounterNotifyEvent.readCounterNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 1) {
      return AlarmNotifyEvent.readAlarmNotifyEvent(firstEvent, sentEvent, in);
    }
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    if(code - firstError == 0) {
      return CounterError.readCounterError(firstError, in);
    }
    if(code - firstError == 1) {
      return AlarmError.readAlarmError(firstError, in);
    }
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(boolean sentEvent, byte extension, short sequenceNumber,
      int length, short eventType, X11Input in) throws IOException {
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
