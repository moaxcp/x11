package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class AlarmNotifyEvent implements XEvent, SyncObject {
  public static final byte NUMBER = 1;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte kind;

  private short sequenceNumber;

  private int alarm;

  @NonNull
  private Int64 counterValue;

  @NonNull
  private Int64 alarmValue;

  private int timestamp;

  private byte state;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static AlarmNotifyEvent readAlarmNotifyEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    AlarmNotifyEvent.AlarmNotifyEventBuilder javaBuilder = AlarmNotifyEvent.builder();
    byte kind = in.readCard8();
    short sequenceNumber = in.readCard16();
    int alarm = in.readCard32();
    Int64 counterValue = Int64.readInt64(in);
    Int64 alarmValue = Int64.readInt64(in);
    int timestamp = in.readCard32();
    byte state = in.readCard8();
    byte[] pad8 = in.readPad(3);
    javaBuilder.kind(kind);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.alarm(alarm);
    javaBuilder.counterValue(counterValue);
    javaBuilder.alarmValue(alarmValue);
    javaBuilder.timestamp(timestamp);
    javaBuilder.state(state);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(kind);
    out.writeCard16(sequenceNumber);
    out.writeCard32(alarm);
    counterValue.write(out);
    alarmValue.write(out);
    out.writeCard32(timestamp);
    out.writeCard8(state);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class AlarmNotifyEventBuilder {
    public AlarmNotifyEvent.AlarmNotifyEventBuilder state(Alarmstate state) {
      this.state = (byte) state.getValue();
      return this;
    }

    public AlarmNotifyEvent.AlarmNotifyEventBuilder state(byte state) {
      this.state = state;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
