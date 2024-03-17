package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ChangeDeviceNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte NUMBER = 12;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte deviceId;

  private short sequenceNumber;

  private int time;

  private byte request;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ChangeDeviceNotifyEvent readChangeDeviceNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    ChangeDeviceNotifyEvent.ChangeDeviceNotifyEventBuilder javaBuilder = ChangeDeviceNotifyEvent.builder();
    byte deviceId = in.readByte();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte request = in.readCard8();
    byte[] pad5 = in.readPad(23);
    javaBuilder.deviceId(deviceId);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.request(request);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writeByte(deviceId);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard8(request);
    out.writePad(23);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ChangeDeviceNotifyEventBuilder {
    public ChangeDeviceNotifyEvent.ChangeDeviceNotifyEventBuilder request(ChangeDevice request) {
      this.request = (byte) request.getValue();
      return this;
    }

    public ChangeDeviceNotifyEvent.ChangeDeviceNotifyEventBuilder request(byte request) {
      this.request = request;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
