package com.github.moaxcp.x11client.protocol.damage;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.xproto.Rectangle;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class NotifyEvent implements XEvent, DamageObject {
  public static final byte NUMBER = 0;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte level;

  private short sequenceNumber;

  private int drawable;

  private int damage;

  private int timestamp;

  @NonNull
  private Rectangle area;

  @NonNull
  private Rectangle geometry;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static NotifyEvent readNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in)
      throws IOException {
    NotifyEvent.NotifyEventBuilder javaBuilder = NotifyEvent.builder();
    byte level = in.readCard8();
    short sequenceNumber = in.readCard16();
    int drawable = in.readCard32();
    int damage = in.readCard32();
    int timestamp = in.readCard32();
    Rectangle area = Rectangle.readRectangle(in);
    Rectangle geometry = Rectangle.readRectangle(in);
    javaBuilder.level(level);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.drawable(drawable);
    javaBuilder.damage(damage);
    javaBuilder.timestamp(timestamp);
    javaBuilder.area(area);
    javaBuilder.geometry(geometry);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(level);
    out.writeCard16(sequenceNumber);
    out.writeCard32(drawable);
    out.writeCard32(damage);
    out.writeCard32(timestamp);
    area.write(out);
    geometry.write(out);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class NotifyEventBuilder {
    public NotifyEvent.NotifyEventBuilder level(ReportLevel level) {
      this.level = (byte) level.getValue();
      return this;
    }

    public NotifyEvent.NotifyEventBuilder level(byte level) {
      this.level = level;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
