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
public class CounterNotifyEvent implements XEvent, SyncObject {
  public static final byte NUMBER = 0;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte kind;

  private short sequenceNumber;

  private int counter;

  @NonNull
  private Int64 waitValue;

  @NonNull
  private Int64 counterValue;

  private int timestamp;

  private short count;

  private boolean destroyed;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static CounterNotifyEvent readCounterNotifyEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    CounterNotifyEvent.CounterNotifyEventBuilder javaBuilder = CounterNotifyEvent.builder();
    byte kind = in.readCard8();
    short sequenceNumber = in.readCard16();
    int counter = in.readCard32();
    Int64 waitValue = Int64.readInt64(in);
    Int64 counterValue = Int64.readInt64(in);
    int timestamp = in.readCard32();
    short count = in.readCard16();
    boolean destroyed = in.readBool();
    byte[] pad9 = in.readPad(1);
    javaBuilder.kind(kind);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.counter(counter);
    javaBuilder.waitValue(waitValue);
    javaBuilder.counterValue(counterValue);
    javaBuilder.timestamp(timestamp);
    javaBuilder.count(count);
    javaBuilder.destroyed(destroyed);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(kind);
    out.writeCard16(sequenceNumber);
    out.writeCard32(counter);
    waitValue.write(out);
    counterValue.write(out);
    out.writeCard32(timestamp);
    out.writeCard16(count);
    out.writeBool(destroyed);
    out.writePad(1);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class CounterNotifyEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
