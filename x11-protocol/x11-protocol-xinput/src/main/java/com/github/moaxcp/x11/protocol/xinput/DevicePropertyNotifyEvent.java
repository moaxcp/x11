package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.xproto.Property;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DevicePropertyNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte NUMBER = 16;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte state;

  private short sequenceNumber;

  private int time;

  private int property;

  private byte deviceId;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static DevicePropertyNotifyEvent readDevicePropertyNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    DevicePropertyNotifyEvent.DevicePropertyNotifyEventBuilder javaBuilder = DevicePropertyNotifyEvent.builder();
    byte state = in.readByte();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    int property = in.readCard32();
    byte[] pad5 = in.readPad(19);
    byte deviceId = in.readCard8();
    javaBuilder.state(state);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.property(property);
    javaBuilder.deviceId(deviceId);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writeByte(state);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard32(property);
    out.writePad(19);
    out.writeCard8(deviceId);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DevicePropertyNotifyEventBuilder {
    public DevicePropertyNotifyEvent.DevicePropertyNotifyEventBuilder state(Property state) {
      this.state = (byte) state.getValue();
      return this;
    }

    public DevicePropertyNotifyEvent.DevicePropertyNotifyEventBuilder state(byte state) {
      this.state = state;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
