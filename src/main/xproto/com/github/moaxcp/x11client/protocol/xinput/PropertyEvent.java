package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PropertyEvent implements XGenericEvent, XinputObject {
  public static final byte NUMBER = 35;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte extension;

  private short sequenceNumber;

  private short eventType;

  private short deviceid;

  private int time;

  private int property;

  private byte what;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static PropertyEvent readPropertyEvent(byte firstEventOffset, boolean sentEvent,
      byte extension, short sequenceNumber, int length, short eventType, X11Input in) throws
      IOException {
    PropertyEvent.PropertyEventBuilder javaBuilder = PropertyEvent.builder();
    short deviceid = in.readCard16();
    int time = in.readCard32();
    int property = in.readCard32();
    byte what = in.readCard8();
    byte[] pad9 = in.readPad(11);
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.deviceid(deviceid);
    javaBuilder.time(time);
    javaBuilder.property(property);
    javaBuilder.what(what);

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
    out.writeCard16(deviceid);
    out.writeCard32(time);
    out.writeCard32(property);
    out.writeCard8(what);
    out.writePad(11);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class PropertyEventBuilder {
    public PropertyEvent.PropertyEventBuilder what(PropertyFlag what) {
      this.what = (byte) what.getValue();
      return this;
    }

    public PropertyEvent.PropertyEventBuilder what(byte what) {
      this.what = what;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
