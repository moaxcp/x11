package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DevicePresenceNotifyEvent implements XEvent, XinputObject {
  public static final byte NUMBER = 15;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private int time;

  private byte devchange;

  private byte deviceId;

  private short control;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static DevicePresenceNotifyEvent readDevicePresenceNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    DevicePresenceNotifyEvent.DevicePresenceNotifyEventBuilder javaBuilder = DevicePresenceNotifyEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte devchange = in.readByte();
    byte deviceId = in.readByte();
    short control = in.readCard16();
    byte[] pad7 = in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.devchange(devchange);
    javaBuilder.deviceId(deviceId);
    javaBuilder.control(control);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeByte(devchange);
    out.writeByte(deviceId);
    out.writeCard16(control);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class DevicePresenceNotifyEventBuilder {
    public DevicePresenceNotifyEvent.DevicePresenceNotifyEventBuilder devchange(
        DeviceChange devchange) {
      this.devchange = (byte) devchange.getValue();
      return this;
    }

    public DevicePresenceNotifyEvent.DevicePresenceNotifyEventBuilder devchange(byte devchange) {
      this.devchange = devchange;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
