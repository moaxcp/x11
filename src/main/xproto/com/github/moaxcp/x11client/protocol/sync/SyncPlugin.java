package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XError;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class SyncPlugin implements XProtocolPlugin {
  public static final String NAME = "SYNC";

  @Getter
  private byte majorVersion = 3;

  @Getter
  private byte minorVersion = 1;

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
    if(request instanceof Initialize) {
      return true;
    }
    if(request instanceof ListSystemCounters) {
      return true;
    }
    if(request instanceof CreateCounter) {
      return true;
    }
    if(request instanceof DestroyCounter) {
      return true;
    }
    if(request instanceof QueryCounter) {
      return true;
    }
    if(request instanceof Await) {
      return true;
    }
    if(request instanceof ChangeCounter) {
      return true;
    }
    if(request instanceof SetCounter) {
      return true;
    }
    if(request instanceof CreateAlarm) {
      return true;
    }
    if(request instanceof ChangeAlarm) {
      return true;
    }
    if(request instanceof DestroyAlarm) {
      return true;
    }
    if(request instanceof QueryAlarm) {
      return true;
    }
    if(request instanceof SetPriority) {
      return true;
    }
    if(request instanceof GetPriority) {
      return true;
    }
    if(request instanceof CreateFence) {
      return true;
    }
    if(request instanceof TriggerFence) {
      return true;
    }
    if(request instanceof ResetFence) {
      return true;
    }
    if(request instanceof DestroyFence) {
      return true;
    }
    if(request instanceof QueryFence) {
      return true;
    }
    if(request instanceof AwaitFence) {
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
