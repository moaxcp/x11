package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class IndicatorMapNotifyEvent implements XEvent, XkbObject {
  public static final byte NUMBER = 5;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte xkbType;

  private short sequenceNumber;

  private int time;

  private byte deviceID;

  private int state;

  private int mapChanged;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static IndicatorMapNotifyEvent readIndicatorMapNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    IndicatorMapNotifyEvent.IndicatorMapNotifyEventBuilder javaBuilder = IndicatorMapNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte[] pad5 = in.readPad(3);
    int state = in.readCard32();
    int mapChanged = in.readCard32();
    byte[] pad8 = in.readPad(12);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.state(state);
    javaBuilder.mapChanged(mapChanged);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(xkbType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard8(deviceID);
    out.writePad(3);
    out.writeCard32(state);
    out.writeCard32(mapChanged);
    out.writePad(12);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class IndicatorMapNotifyEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
