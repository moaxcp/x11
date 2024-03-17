package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.xproto.Mapping;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeviceMappingNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte NUMBER = 11;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte deviceId;

  private short sequenceNumber;

  private byte request;

  private byte firstKeycode;

  private byte count;

  private int time;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static DeviceMappingNotifyEvent readDeviceMappingNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    DeviceMappingNotifyEvent.DeviceMappingNotifyEventBuilder javaBuilder = DeviceMappingNotifyEvent.builder();
    byte deviceId = in.readByte();
    short sequenceNumber = in.readCard16();
    byte request = in.readCard8();
    byte firstKeycode = in.readCard8();
    byte count = in.readCard8();
    byte[] pad6 = in.readPad(1);
    int time = in.readCard32();
    byte[] pad8 = in.readPad(20);
    javaBuilder.deviceId(deviceId);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.request(request);
    javaBuilder.firstKeycode(firstKeycode);
    javaBuilder.count(count);
    javaBuilder.time(time);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writeByte(deviceId);
    out.writeCard16(sequenceNumber);
    out.writeCard8(request);
    out.writeCard8(firstKeycode);
    out.writeCard8(count);
    out.writePad(1);
    out.writeCard32(time);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeviceMappingNotifyEventBuilder {
    public DeviceMappingNotifyEvent.DeviceMappingNotifyEventBuilder request(Mapping request) {
      this.request = (byte) request.getValue();
      return this;
    }

    public DeviceMappingNotifyEvent.DeviceMappingNotifyEventBuilder request(byte request) {
      this.request = request;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
