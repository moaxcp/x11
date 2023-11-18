package com.github.moaxcp.x11client.protocol.present;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CompleteNotifyEvent implements XGenericEvent, PresentObject {
  public static final byte NUMBER = 35;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte extension;

  private short sequenceNumber;

  private short eventType;

  private byte kind;

  private byte mode;

  private int event;

  private int window;

  private int serial;

  private long ust;

  private long msc;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static CompleteNotifyEvent readCompleteNotifyEvent(byte firstEventOffset,
      boolean sentEvent, byte extension, short sequenceNumber, int length, short eventType,
      X11Input in) throws IOException {
    CompleteNotifyEvent.CompleteNotifyEventBuilder javaBuilder = CompleteNotifyEvent.builder();
    byte kind = in.readCard8();
    byte mode = in.readCard8();
    int event = in.readCard32();
    int window = in.readCard32();
    int serial = in.readCard32();
    long ust = in.readCard64();
    long msc = in.readCard64();
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.kind(kind);
    javaBuilder.mode(mode);
    javaBuilder.event(event);
    javaBuilder.window(window);
    javaBuilder.serial(serial);
    javaBuilder.ust(ust);
    javaBuilder.msc(msc);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(extension);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength() - 32);
    out.writeCard16(eventType);
    out.writeCard8(kind);
    out.writeCard8(mode);
    out.writeCard32(event);
    out.writeCard32(window);
    out.writeCard32(serial);
    out.writeCard64(ust);
    out.writeCard64(msc);
  }

  @Override
  public int getSize() {
    return 40;
  }

  public static class CompleteNotifyEventBuilder {
    public CompleteNotifyEvent.CompleteNotifyEventBuilder kind(CompleteKind kind) {
      this.kind = (byte) kind.getValue();
      return this;
    }

    public CompleteNotifyEvent.CompleteNotifyEventBuilder kind(byte kind) {
      this.kind = kind;
      return this;
    }

    public CompleteNotifyEvent.CompleteNotifyEventBuilder mode(CompleteMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public CompleteNotifyEvent.CompleteNotifyEventBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 40;
    }
  }
}
